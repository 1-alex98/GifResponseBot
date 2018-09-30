package sample.telegram.services;

import sample.telegram.reactions.DefaultListeners;
import sample.telegram.dtos.TelegramUpdate;
import sample.telegram.messages.TelegramAnswer;

import java.util.ArrayList;
import java.util.List;

public class BotService implements UpdateProcessor {
    private final BotComService botComService= new BotComService();
    private static BotService ourInstance = new BotService();
    private Boolean running=false;
    private List<MessageListener> messageListeners;


    public static BotService getInstance() {
        return ourInstance;
    }

    public BotService() {
        this.messageListeners = new ArrayList<>();
        new DefaultListeners(this);

    }

    public void registerListener(MessageListener messageListener){
        messageListeners.add(messageListener);
    }

    public void start() {
        if(running){
            return;
        }
        botComService.requestUpdates(this);
        running=true;
    }

    @Override
    public void processUpdate(TelegramUpdate telegramUpdate){
        messageListeners.forEach(messageListener -> botComService.respond(messageListener.processUpdate(telegramUpdate)));
    }

    public void stop() {
        if (!running){
            return;
        }
        botComService.stop();
        DataStoreService.getInstance().save();
        running= false;
    }

    public interface MessageListener{
        List<TelegramAnswer> processUpdate(TelegramUpdate telegramUpdate);
    }
}
