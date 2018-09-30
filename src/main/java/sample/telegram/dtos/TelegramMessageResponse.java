package sample.telegram.dtos;

import lombok.Data;

@Data
public class TelegramMessageResponse {
    private boolean ok;
    private TelegramMessage result;
}
