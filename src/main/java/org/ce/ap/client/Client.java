package main.java.org.ce.ap.client;

import javafx.application.Application;
import javafx.stage.Stage;
import main.java.org.ce.ap.client.services.impl.SceneHandlerImpl;

public class Client extends Application {



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        SceneHandlerImpl.getInstance(primaryStage);
    }
}
