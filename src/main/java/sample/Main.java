package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.BasicConfigurator;
import sample.telegram.services.BotService;

@Log4j
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        BasicConfigurator.configure();
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> log.error("Error in thread "+t.getName(),e) );

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("main.fxml"));
        primaryStage.setTitle("Kartoffel Bot");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> {
            BotService.getInstance().stop();
            primaryStage.close();
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
