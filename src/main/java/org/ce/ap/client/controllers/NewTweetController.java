package main.java.org.ce.ap.client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import main.java.org.ce.ap.client.services.impl.SceneHandlerImpl;
import main.java.org.ce.ap.client.services.impl.UIConnectionService;
import main.java.org.ce.ap.server.jsonHandling.Parameter;
import main.java.org.ce.ap.server.jsonHandling.Request;
import main.java.org.ce.ap.server.jsonHandling.Response;
import main.java.org.ce.ap.server.jsonHandling.impl.parameter.SendTweetParameter;

public class NewTweetController implements DataGetter {

    @FXML
    private TextArea tweetContentArea;

    private int tweetID = -1;

    @FXML
    void OnPost(ActionEvent event) {
        String tweetMsg = tweetContentArea.getText();
        Parameter param = new SendTweetParameter(tweetMsg, tweetID);
        Request req = new Request("SendTweet", "Sends new tweet from logged in user", param);
        Response serverResponse = UIConnectionService.getInstance().sendToServer(req);
        if (serverResponse.getErrorCode() != 0) {
            System.err.println("Error: " + serverResponse.getErrorCode());
        } else {
            Stage stage = (Stage) tweetContentArea.getScene().getWindow();
            stage.close();
        }

    }

    @FXML
    void onBack(ActionEvent event) {
        Stage stage = (Stage) tweetContentArea.getScene().getWindow();
        stage.close();
    }

    /**
     * get data from another class
     *
     * @param data data is the parent TweetID in form of an integer.
     */
    public void getData(Object data) {
        this.tweetID = (Integer) data;
    }
}