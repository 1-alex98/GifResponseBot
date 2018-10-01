package sample;

import lombok.extern.log4j.Log4j;
import sample.telegram.services.BotService;

@Log4j
public class Main {

    public static void main(String[] args) {
        BotService.getInstance().start();
    }
}
