package main.java.org.ce.ap.client.services.impl;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.org.ce.ap.client.controllers.NewTweetController;
import main.java.org.ce.ap.client.services.SceneHandler;
import main.java.org.ce.ap.server.jsonHandling.Request;
import main.java.org.ce.ap.server.jsonHandling.Response;
import main.java.org.ce.ap.server.jsonHandling.impl.parameter.SignInParameter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

//TODO: PERSISTENT DARK MODE
public class SceneHandlerImpl implements SceneHandler {
    Stage primaryStage;
    Scene currentScene;
    boolean isDark;
    boolean isFullScreen = false;

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
        String path = PropertiesServiceImpl.getInstance().getProperty("client.saved.file");
        try (BufferedReader in = new BufferedReader(new FileReader(path))) {
            boolean rememberMe = Boolean.parseBoolean(in.readLine());
            if (rememberMe) {
                String username, password;
                username = in.readLine();
                password = in.readLine();
                SignInParameter param = new SignInParameter(username, password);
                Request req = new Request("SignIn", "Logins to account", param);
                Response serverResponse = UIConnectionService.getInstance().sendToServer(req);
                if (serverResponse.getErrorCode() != 0) {
                    System.err.println("Error, please try again");
                } else {
                    changeScene("/timeline-page.fxml");
                }
            } else {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/login-page.fxml"));
                currentScene = new Scene(fxmlLoader.load(), 400, 600);
                this.primaryStage.setTitle("TwT");
                this.primaryStage.setScene(currentScene);
                this.primaryStage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changeScene(String fxml) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = fxmlLoader.load();
            currentScene = new Scene(root);
            primaryStage.setScene(currentScene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changeScene(String fxml, int data) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = fxmlLoader.load();
            NewTweetController controller = fxmlLoader.<NewTweetController>getController();
            controller.setTweetID(data);
            currentScene = new Scene(root);
            primaryStage.setScene(currentScene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void newWindow(String fxml, String title) {
        try {
            FXMLLoader fLoader = new FXMLLoader(getClass().getResource(fxml));
            Scene scene = new Scene(fLoader.load());
            Stage newStage = new Stage();
            newStage.setTitle(title);
            newStage.setScene(scene);
            newStage.show();
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
        isDark = !isDark;
    }

    @Override
    public void toggleFullScreen() {
        primaryStage.setFullScreen(!isFullScreen);
        isFullScreen = !isFullScreen;
    }
}
