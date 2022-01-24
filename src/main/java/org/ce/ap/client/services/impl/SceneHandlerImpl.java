package main.java.org.ce.ap.client.services.impl;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.org.ce.ap.client.Client;
import main.java.org.ce.ap.client.services.SceneHandler;

import java.io.IOException;
import java.net.URL;

public class SceneHandlerImpl implements SceneHandler {
    Stage primaryStage;
    Scene currentScene;
    FXMLLoader fxmlLoader;
    boolean isDark;

    private static SceneHandlerImpl INSTANCE = null;

    public static SceneHandlerImpl getInstance() {
        return INSTANCE;
    }

    public static SceneHandlerImpl getInstance(Stage primaryStage) {
        if (INSTANCE == null) {
            INSTANCE = new SceneHandlerImpl(primaryStage);
        }
        return INSTANCE;
    }

    private SceneHandlerImpl(Stage primaryStage) {
        this.primaryStage = primaryStage;
        try {
            fxmlLoader = new FXMLLoader(getClass().getResource("/login-page.fxml"));
            currentScene = null;
            currentScene = new Scene(fxmlLoader.load(), 400, 600);
            this.primaryStage.setTitle("TwT");
            this.primaryStage.setScene(currentScene);
            this.primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changeScene(String fxml) {
        try {
            Parent blah = FXMLLoader.load(getClass().getResource(fxml));
            Scene scene = new Scene(blah);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void toggleDarkTheme() {
        if (isDark)
            currentScene.getStylesheets().remove("/dark-theme.css");
        else
            currentScene.getStylesheets().add("/dark-theme.css");
    }
}
