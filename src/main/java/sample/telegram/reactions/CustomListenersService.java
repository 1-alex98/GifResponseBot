package sample.telegram.reactions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import sample.giphy.TenorComService;
import sample.telegram.messages.SendAnimation;
import sample.telegram.messages.TelegramAnswer;

import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Log4j
public class CustomListenersService {
    private static CustomListenersService ourInstance = new CustomListenersService();
    private HashMap<TriggerData,String> customTrigger= new HashMap<>();

    public static CustomListenersService getInstance() {
        if(ourInstance==null){
            ourInstance= new CustomListenersService();
        }
        return ourInstance;
    }

    private CustomListenersService() {
        try {
            FileReader fileReader = new FileReader("out.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String collect = bufferedReader.lines().collect(Collectors.joining());
            Object o = new Gson().fromJson(collect, new TypeToken<HashMap<TriggerData, String>>() {}.getType());
            customTrigger= (HashMap<TriggerData, String>) o;
        } catch (Exception e) {
            log.warn(e);
        }
    }

    public List<TelegramAnswer> getTriggers(String message, int chatId){
        return customTrigger.entrySet().stream()
                    .filter(triggerDataStringEntry -> triggerDataStringEntry.getKey().getChatId()==chatId && message.toLowerCase().contains(triggerDataStringEntry.getKey().getTrigger()))
                    .map(triggerDataStringEntry -> new SendAnimation(triggerDataStringEntry.getKey().getChatId(),TenorComService.getInstance().getRandomGifUrlForWord(triggerDataStringEntry.getValue(),"en_US")))
                    .collect(Collectors.toList());
    }

    public Boolean registerCustomTrigger(int chatId, String s) {
        String[] split = s.split("-");
        if(split.length!=2){
            return false;
        }

        customTrigger.put(new TriggerData(chatId,split[0]),split[1]);
        save();

        return true;
    }

    public void save() {
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization()
                .setPrettyPrinting().create();
        String s = gson.toJson(customTrigger,new TypeToken<HashMap<TriggerData, String>>() {}.getType());
        try {
            FileWriter fileWriter = new FileWriter("out.txt",false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(s);
            bufferedWriter.flush();
            bufferedWriter.close();
            fileWriter.close();
            ourInstance=null;
        } catch (IOException e) {
            log.error(e);
        }
    }

    public void clear(Integer chatId) {
        List<Map.Entry<TriggerData, String>> collect = customTrigger.entrySet().stream()
                .filter(triggerDataStringEntry -> triggerDataStringEntry.getKey().getChatId() == chatId)
                .collect(Collectors.toList());
        collect.forEach(triggerDataStringEntry -> customTrigger.remove(triggerDataStringEntry.getKey()));
        save();
    }

    @Getter
    @Setter
    @RequiredArgsConstructor
    class TriggerData {
        private final int chatId;
        private final String trigger;
    }
}
