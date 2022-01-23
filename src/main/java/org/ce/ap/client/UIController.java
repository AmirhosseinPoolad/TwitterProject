package main.java.org.ce.ap.client;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.java.org.ce.ap.client.services.ConnectionService;
import main.java.org.ce.ap.client.services.impl.ConnectionServiceImpl;
import main.java.org.ce.ap.server.jsonHandling.Request;
import main.java.org.ce.ap.server.jsonHandling.Response;
import main.java.org.ce.ap.server.jsonHandling.impl.parameter.SignInParameter;

public class UIController {
    //connection service that handles connecting and i/o with server
    ConnectionService connectionService;

    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;

    public UIController() {
        connectionService = new ConnectionServiceImpl();
    }

    @FXML
    protected void onLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        SignInParameter param = new SignInParameter(username, password);
        Request req = new Request("SignIn", "Logins to account", param);
        Response serverResponse = connectionService.sendToServer(req);
        if (serverResponse.getErrorCode() != 0) {
            System.err.println("Error, please try again");
        } else {
            System.out.println("Successfully logged in.");
            //TODO: GO TO TIMELINE
        }
    }
}
