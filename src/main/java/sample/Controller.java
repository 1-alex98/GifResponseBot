package sample;

import javafx.event.ActionEvent;
import sample.telegram.services.BotService;

public class Controller {
    private static final BotService botService = new BotService();
    public void start(ActionEvent actionEvent) {
        botService.start();
    }

    public void stop(ActionEvent actionEvent) {
        botService.stop();
    }
}
