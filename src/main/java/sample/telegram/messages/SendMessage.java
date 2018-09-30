package sample.telegram.messages;

import lombok.Data;

@Data
public class SendMessage extends TelegramAnswer {
    private final String text;

    public SendMessage(int ChatId, String text) {
        super(ChatId);
        this.text = text;
    }
}
