package de.vontrostorff;

import de.vontrostorff.telegram.services.BotService;
import org.apache.log4j.BasicConfigurator;

public class Main {

    public static void main(String[] args) {
        BasicConfigurator.configure(); // for logging
        BotService.getInstance().start();
    }
}
