package de.vontrostorff.telegram.reactions;

import de.vontrostorff.giphy.TenorComService;
import de.vontrostorff.telegram.messages.SendAnimation;
import de.vontrostorff.telegram.messages.SendMessage;
import de.vontrostorff.telegram.services.BotService;
import de.vontrostorff.telegram.services.DataStoreService;

import java.time.OffsetDateTime;
import java.util.Collections;

public class DefaultListeners {
    public DefaultListeners(BotService instance) {


        //Gif on Kartoffel
        instance.registerListener(telegramUpdate -> {
            String text = telegramUpdate.getMessage().getText();
            if(text==null)return Collections.emptyList();
            String s = text.toLowerCase();
            if(s.contains("kartoffel") || s.contains("potato") ){
                return Collections.singletonList(new SendAnimation(telegramUpdate.getMessage().getChat().getId(),TenorComService.getInstance().getRandomGifUrlForWord("potato","en_US")));
            }
            return Collections.emptyList();
        });

        //Custom trigger trigger
        instance.registerListener(telegramUpdate -> {
            String text = telegramUpdate.getMessage().getText();
            if(text==null)return Collections.emptyList();
            Integer chatId = telegramUpdate.getMessage().getChat().getId();
            String message = telegramUpdate.getMessage().getText();
            return CustomListenersService.getInstance().getTriggers(message,chatId);
        });

        //Custom triggers registering
        instance.registerListener(telegramUpdate -> {
            String text = telegramUpdate.getMessage().getText();
            if(text==null)return Collections.emptyList();
            String s = telegramUpdate.getMessage().getText().toLowerCase();
            Integer userId = telegramUpdate.getMessage().getFrom().getId();
            Integer chatId = telegramUpdate.getMessage().getChat().getId();
            if(s.contains("/register")){
                DataStoreService.getInstance().registerUserForTrigger(userId);
                return Collections.singletonList(new SendMessage(chatId,"Please provide the word the bot should be looking for and the search phrase for gifs. The trigger only works for this chat!\nFormat: (trigger)-(search word) \nExample: fried potato-chips"));
            }else {
                OffsetDateTime dateOfLastRegisterCommand = DataStoreService.getInstance().getDateOfLastRegisterCommand(userId);
                if(dateOfLastRegisterCommand!=null&&dateOfLastRegisterCommand.isBefore(OffsetDateTime.now().plusMinutes(3))){
                    Boolean success= CustomListenersService.getInstance().registerCustomTrigger(chatId,s);
                    DataStoreService.getInstance().removeLastCommandDate(userId);
                    return Collections.singletonList(new SendMessage(chatId,success?"Success...":"Error, wrong format!!!"));
                }else if (s.contains("/clear")) {
                    CustomListenersService.getInstance().clear(chatId);
                }
            }
            return Collections.emptyList();
        });

        //list all commands
        instance.registerListener(telegramUpdate -> {
            String text = telegramUpdate.getMessage().getText();
            if (text == null) return Collections.emptyList();
            if (text.contains("/list")) {
                return CustomListenersService.getInstance().getTriggerList(telegramUpdate);
            }
            return Collections.emptyList();
        });


    }
}
