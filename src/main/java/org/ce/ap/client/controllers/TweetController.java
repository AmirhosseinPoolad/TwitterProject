package main.java.org.ce.ap.client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import main.java.org.ce.ap.server.entity.Tweet;

import java.io.IOException;

public class TweetController extends ListCell<Tweet> {

    @FXML
    private VBox box;

    @FXML
    private Text usernameText;

    @FXML
    private Text dateText;

    @FXML
    private Text tweetContentText;

    @FXML
    private ListView<?> repliesListView;

    private FXMLLoader mLLoader;

    @FXML
    void initialize() {

    }

    @FXML
    void onLike(ActionEvent event) {

    }

    @FXML
    void onReply(ActionEvent event) {

    }

    @FXML
    void onRetweet(ActionEvent event) {

    }

    @FXML
    void onViewProfile(ActionEvent event) {

    }


    protected void updateItem(Tweet item, boolean empty) {
        // required to ensure that cell displays properly
        super.updateItem(item, empty);

        if (empty || item == null) {
            setGraphic(null); // don't display anything
        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/tweet.fxml"));
                mLLoader.setController(this);
                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            usernameText.setText(item.getPoster());
            dateText.setText(item.getPostTime().toString());
            tweetContentText.setText(item.getContent());
            //TODO: REPLIES?
            setGraphic(box); // attach custom layout to ListView cell
        }
    }
}
