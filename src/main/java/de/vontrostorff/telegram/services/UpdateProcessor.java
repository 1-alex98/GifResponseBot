package de.vontrostorff.telegram.services;

import de.vontrostorff.telegram.dtos.TelegramUpdate;

public interface UpdateProcessor {
    void processUpdate(TelegramUpdate telegramUpdate);
}
