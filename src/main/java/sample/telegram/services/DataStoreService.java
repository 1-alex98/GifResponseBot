package sample.telegram.services;

import com.google.gson.Gson;
import lombok.Data;
import sample.telegram.dtos.TelegramUpdate;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataStoreService {
    private static DataStoreService ourInstance = new DataStoreService();
    private List<Integer> registeredUpdates = new ArrayList<>();
    private Map<Integer, OffsetDateTime> usersCommandTrigger= new HashMap<>();

    public static DataStoreService getInstance() {
        return ourInstance;
    }


    private DataStoreService() {
    }

    /**
     * register a update as processed
     * @param telegramUpdate
     * @return true if already registered
     */
    public boolean registerUpdate(TelegramUpdate telegramUpdate) {
        if(registeredUpdates.contains(telegramUpdate.getUpdateId())){
            return true;
        }
        registeredUpdates.add(telegramUpdate.getUpdateId());
        return false;
    }

    public void save() {
        //TODO:save
    }

    public void registerUserForTrigger(Integer id) {
        usersCommandTrigger.put(id,OffsetDateTime.now());
    }

    public OffsetDateTime getDateOfLastRegisterCommand(Integer id){
        return usersCommandTrigger.get(id);
    }

    public void removeLastCommandDate(Integer userId) {
        usersCommandTrigger.remove(userId);
    }
}
