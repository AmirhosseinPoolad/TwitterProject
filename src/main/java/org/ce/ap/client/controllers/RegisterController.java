package main.java.org.ce.ap.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.java.org.ce.ap.client.MenuStatus;
import main.java.org.ce.ap.client.services.impl.SceneHandlerImpl;
import main.java.org.ce.ap.client.services.impl.UIConnectionService;
import main.java.org.ce.ap.server.jsonHandling.Request;
import main.java.org.ce.ap.server.jsonHandling.Response;
import main.java.org.ce.ap.server.jsonHandling.impl.parameter.RegisterParameter;

import java.time.LocalDate;

public class RegisterController {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField fnameField;
    @FXML
    private TextField lnameField;
    @FXML
    private TextArea bioField;
    @FXML
    private DatePicker birthdayDatePicker;

    @FXML
    public void initialize() {
        bioField.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= 255 ? change : null));
    }

    @FXML
    protected void onRegister() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String firstName = fnameField.getText();
        String lastName = lnameField.getText();
        String biography = bioField.getText();
        LocalDate birthdayDate = birthdayDatePicker.getValue();
        RegisterParameter param = new RegisterParameter(username, password, firstName, lastName, biography, birthdayDate);
        Request req = new Request("Register", "Registers a new account and logs in", param);
        Response serverResponse = UIConnectionService.getInstance().sendToServer(req);
        if (serverResponse.getErrorCode() != 0) {
            System.err.println("Error, please try again");
        } else {
            System.out.println("Succesfully registered.");
            SceneHandlerImpl.getInstance().changeScene("/timeline-page.fxml");
        }
    }
}
