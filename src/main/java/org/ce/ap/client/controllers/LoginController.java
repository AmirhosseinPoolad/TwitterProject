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

    public LoginController() {
        connectionService = UIConnectionService.getInstance();
        String path = PropertiesServiceImpl.getInstance().getProperty("client.saved.file");
        try (BufferedReader in = new BufferedReader(new FileReader(path))) {
            boolean rememberMe = Boolean.parseBoolean(in.readLine());
            //TODO:UNCOMMENT NEXT LINES WHEN TIMELINE IS FUNCTIONAL
        /*if (rememberMe) {

                String username, password;
                username = in.readLine();
                password = in.readLine();
                login(username,password);*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        login(username, password);

    }

    private void login(String username, String password) {
        SignInParameter param = new SignInParameter(username, password);
        Request req = new Request("SignIn", "Logins to account", param);
        Response serverResponse = connectionService.sendToServer(req);
        if (serverResponse.getErrorCode() != 0) {
            System.err.println("Error, please try again");
        } else {
            System.out.println("Successfully logged in.");
            //save remember me status in properties file
            boolean status = rememberButton.isSelected();
            String boolString = status ? "1" : "0";
            PropertiesServiceImpl.getInstance().setProperty("client.rememberMe", boolString);
            //save remember me and login information
            String path = PropertiesServiceImpl.getInstance().getProperty("client.saved.file");
            try (BufferedWriter out = new BufferedWriter(new FileWriter(path))) {
                out.write(String.valueOf(rememberButton.isSelected()));
                if (rememberButton.isSelected()) {
                    out.newLine();
                    out.write(username);
                    out.newLine();
                    out.write(password);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            SceneHandlerImpl.getInstance().changeScene("/timeline-page.fxml");
        }
    }

    @FXML
    protected void onRegister() {
        //change scene to register-page.fxml
        SceneHandlerImpl.getInstance().changeScene("/register-page.fxml");
    }
}
