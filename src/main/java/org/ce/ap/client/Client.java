package main.java.org.ce.ap.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Client extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Client.class.getResource("/login-page.fxml"));
            Scene loginScene = null;
            loginScene = new Scene(fxmlLoader.load(), 400, 600);
            primaryStage.setTitle("TwT");
            primaryStage.setScene(loginScene);
            primaryStage.show();
            //toggle dark theme
            loginScene.getStylesheets().add("/dark-theme.css");
            //untoggle dark theme
            //loginScene.getStylesheets().remove("/dark-theme.css");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
