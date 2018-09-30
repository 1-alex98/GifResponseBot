package sample.telegram.services;

import sample.telegram.dtos.TelegramUpdate;

public interface UpdateProcessor {
    void processUpdate(TelegramUpdate telegramUpdate);
}
