package main.java.org.ce.ap.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.java.org.ce.ap.client.services.ConnectionService;
import main.java.org.ce.ap.client.services.impl.PropertiesServiceImpl;
import main.java.org.ce.ap.client.services.impl.SceneHandlerImpl;
import main.java.org.ce.ap.client.services.impl.UIConnectionService;
import main.java.org.ce.ap.server.jsonHandling.Request;
import main.java.org.ce.ap.server.jsonHandling.Response;
import main.java.org.ce.ap.server.jsonHandling.impl.parameter.SignInParameter;

import java.io.*;

public class LoginController {
    //connection service that handles connecting and i/o with server
    private ConnectionService connectionService;

    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private CheckBox rememberButton;

    @FXML
    public void initialize() {
        connectionService = UIConnectionService.getInstance();
    }

    @FXML
    protected void onLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        login(username, password, rememberButton.isSelected());

    }

    private void login(String username, String password, boolean rememberMe) {
        SignInParameter param = new SignInParameter(username, password);
        Request req = new Request("SignIn", "Logins to account", param);
        Response serverResponse = connectionService.sendToServer(req);
        if (serverResponse.getErrorCode() != 0) {
            System.err.println("Error, please try again");
        } else {
            System.out.println("Successfully logged in.");
            //save remember me and login information
            String path = PropertiesServiceImpl.getInstance().getProperty("client.saved.file");
            try (BufferedWriter out = new BufferedWriter(new FileWriter(path))) {
                out.write(String.valueOf(rememberMe));
                if (rememberMe) {
                    out.newLine();
                    out.write(username);
                    out.newLine();
                    out.write(password);
                }
                SceneHandlerImpl.getInstance().changeScene("/timeline-page.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @FXML
    protected void onRegister() {
        SceneHandlerImpl.getInstance().changeScene("/register-page.fxml");
    }
}
