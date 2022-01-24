package main.java.org.ce.ap.client.controllers;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import main.java.org.ce.ap.client.services.impl.SceneHandlerImpl;

public class MainController {

    @FXML
    void onAbout(ActionEvent event) {
        //TODO: ABOUT PAGE
    }

    @FXML
    void onDarkModeToggle(ActionEvent event) {
        SceneHandlerImpl.getInstance().toggleDarkTheme();
    }

    @FXML
    void onExit(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    void onFullScreen(ActionEvent event) {
        SceneHandlerImpl.getInstance().toggleFullScreen();
    }

    @FXML
    void onHelp(ActionEvent event) {
        //TODO: HELP PAGE
    }

    @FXML
    void onLogOut(ActionEvent event) {
        //TODO: SERVERSIDE LOGOUT FUNCTIONALITY
    }

    @FXML
    void onOptions(ActionEvent event) {
        //TODO: OPTIONS PAGE
    }

}