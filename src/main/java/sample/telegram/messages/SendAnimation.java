package sample.telegram.messages;

import lombok.Data;

@Data
public class SendAnimation extends TelegramAnswer {
    private final String animationUrl;

    public SendAnimation(int ChatId, String animationUrl) {
        super(ChatId);
        this.animationUrl = animationUrl;
    }
}
