package sample.telegram.reactions;

import lombok.Data;
import sample.giphy.TenorComService;
import sample.telegram.messages.SendAnimation;
import sample.telegram.messages.TelegramAnswer;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CustomListenersService {
    private static CustomListenersService ourInstance = new CustomListenersService();
    private HashMap<TriggerData,String> customTrigger= new HashMap<>();

    public static CustomListenersService getInstance() {
        return ourInstance;
    }

    private CustomListenersService() {
    }

    public List<TelegramAnswer> getTriggers(String message, int chatId){
        return customTrigger.entrySet().stream()
                    .filter(triggerDataStringEntry -> triggerDataStringEntry.getKey().getChatId()==chatId && message.contains(triggerDataStringEntry.getKey().getTrigger()))
                    .map(triggerDataStringEntry -> new SendAnimation(triggerDataStringEntry.getKey().getChatId(),TenorComService.getInstance().getRandomGifUrlForWord(triggerDataStringEntry.getValue(),"en_US")))
                    .collect(Collectors.toList());
    }

    public Boolean registerCustomTrigger(int chatId, String s) {
        String[] split = s.split("-");
        if(split.length!=2){
            return false;
        }

        customTrigger.put(new TriggerData(chatId,split[0]),split[1]);

        return true;
    }
    @Data
    class TriggerData {
        private final int chatId;
        private final String trigger;
    }
}
