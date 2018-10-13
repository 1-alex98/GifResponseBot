package de.vontrostorff.telegram.messages;

import lombok.Data;

@Data
public class SendMessage extends TelegramAnswer {
    private final String text;
    private String contentType;

    public SendMessage(int ChatId, String text) {
        super(ChatId);
        this.text = text;
    }

    public SendMessage(int ChatId, String text, String contentType) {
        super(ChatId);
        this.text = text;
        this.contentType = contentType;
    }
}
