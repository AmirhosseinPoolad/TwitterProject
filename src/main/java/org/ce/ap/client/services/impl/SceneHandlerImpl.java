package main.java.org.ce.ap.client.services.impl;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.org.ce.ap.client.controllers.DataGetter;
import main.java.org.ce.ap.client.services.SceneHandler;
import main.java.org.ce.ap.server.jsonHandling.Request;
import main.java.org.ce.ap.server.jsonHandling.Response;
import main.java.org.ce.ap.server.jsonHandling.impl.parameter.SignInParameter;
import main.java.org.ce.ap.server.jsonHandling.impl.result.UserResult;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * handles creating and changing scenes and transitions between them
 */
public class SceneHandlerImpl implements SceneHandler {
    //primary stage of the application
    Stage primaryStage;
    //current scene of the application
    Scene currentScene;
    //is dark mode on
    boolean isDark = false;
    private final String darkModePath;
    //is fullscreen
    boolean isFullScreen = false;

    //singleton stuff
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

    /**
     * constructor that loads the login page of if remember me is on goes to the timeline itself.
     * @param primaryStage primary stage of the application
     */
    private SceneHandlerImpl(Stage primaryStage) {
        this.primaryStage = primaryStage;
        String path = PropertiesServiceImpl.getInstance().getProperty("client.saved.file");
        darkModePath = PropertiesServiceImpl.getInstance().getProperty("client.dark.css");
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
                    String fxml = PropertiesServiceImpl.getInstance().getProperty("client.timeline.page");
                    changeScene(fxml, ((UserResult) serverResponse.getResults()).getUser());
                }
            } else {
                String fxml = PropertiesServiceImpl.getInstance().getProperty("client.login.page");
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
                currentScene = new Scene(fxmlLoader.load(), 400, 600);
                this.primaryStage.setTitle("TwT");
                this.primaryStage.setScene(currentScene);
                this.primaryStage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * changes scene to fxml file
     * @param fxml address of fxml file
     */
    @Override
    public void changeScene(String fxml) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = fxmlLoader.load();
            currentScene = new Scene(root);
            primaryStage.setScene(currentScene);
            if (isDark)
                currentScene.getStylesheets().add(darkModePath);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * changes scene to fxml file and passes the data to it.
     * @param fxml address of fxml file
     * @param data data to be passed
     */
    @Override
    public void changeScene(String fxml, Object data) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = fxmlLoader.load();
            DataGetter controller = fxmlLoader.getController();
            controller.getData(data);
            currentScene = new Scene(root);
            if (isDark)
                currentScene.getStylesheets().add(darkModePath);
            primaryStage.setScene(currentScene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * opens new window with fxml file and title
     * @param fxml address of fxml file
     * @param title title of new window
     */
    @Override
    public void newWindow(String fxml, String title) {
        try {
            FXMLLoader fLoader = new FXMLLoader(getClass().getResource(fxml));
            Scene scene = new Scene(fLoader.load());
            Stage newStage = new Stage();
            newStage.setTitle(title);
            newStage.setScene(scene);
            if (isDark)
                scene.getStylesheets().add(darkModePath);
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * opens new window with fxml file and title and passes data to it
     * @param fxml address of fxml file
     * @param title title of new window
     * @param data data to be passed
     */
    @Override
    public void newWindow(String fxml, String title, Object data) {
        try {
            FXMLLoader fLoader = new FXMLLoader(getClass().getResource(fxml));
            Scene scene = new Scene(fLoader.load());
            DataGetter controller = fLoader.getController();
            controller.getData(data);
            Stage newStage = new Stage();
            newStage.setTitle(title);
            newStage.setScene(scene);
            if (isDark)
                scene.getStylesheets().add(darkModePath);
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * toggles dark theme
     */
    @Override
    public void toggleDarkTheme() {
        if (isDark)
            currentScene.getStylesheets().remove(darkModePath);
        else
            currentScene.getStylesheets().add(darkModePath);
        isDark = !isDark;
    }

    /**
     * toggles fullscreen
     */
    @Override
    public void toggleFullScreen() {
        primaryStage.setFullScreen(!isFullScreen);
        isFullScreen = !isFullScreen;
    }
}
