package main.java.org.ce.ap.client.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.util.Callback;
import main.java.org.ce.ap.client.MenuStatus;
import main.java.org.ce.ap.client.services.impl.UIConnectionService;
import main.java.org.ce.ap.server.entity.Tweet;
import main.java.org.ce.ap.server.entity.User;
import main.java.org.ce.ap.server.jsonHandling.Parameter;
import main.java.org.ce.ap.server.jsonHandling.Request;
import main.java.org.ce.ap.server.jsonHandling.Response;
import main.java.org.ce.ap.server.jsonHandling.impl.parameter.GetProfileParameter;
import main.java.org.ce.ap.server.jsonHandling.impl.result.GetProfileResult;
import main.java.org.ce.ap.server.jsonHandling.impl.result.GetTimelineResult;
import main.java.org.ce.ap.server.util.Tree;

import java.util.ArrayList;

public class ProfileController implements DataGetter {

    private String username;
    private int isFollowing;
    private User user;
    @FXML
    private Text usernameText;
    @FXML
    private Text bioText;
    @FXML
    private ListView<Tree<Tweet>> tweetListView;
    private final ObservableList<Tree<Tweet>> tweets = FXCollections.observableArrayList();
    private ArrayList<Tree<Tweet>> tweetGraph;
    @FXML
    private Button followButton;

    @FXML
    void initialize() {
        Platform.runLater(() -> {
            getProfile();
            usernameText.setText(user.getUsername());
            bioText.setText(user.getBiography());
            if (isFollowing == 1) {
                followButton.setText("Unfollow");
            } else {
                followButton.setText("Follow");
            }
        });

    }

    /**
     * gets the timeline from the server and shows it
     */
    private void getProfile() {
        Parameter param = new GetProfileParameter(username);
        Request req = new Request("GetProfile", "Gets profile of specified user", param);
        Response serverResponse = UIConnectionService.getInstance().sendToServer(req);
        if (serverResponse.getErrorCode() != 0) {
            System.err.println("Error, please try again");
            return;
        }
        GetProfileResult res = (GetProfileResult) serverResponse.getResults();
        this.tweetGraph = res.getTweets();
        this.user = res.getUser();
        Parameter param2 = new GetProfileParameter(username);
        Request req2 = new Request("IsFollowing", "Checks if logged in user is following other user", param2);
        Response serverResponse2 = UIConnectionService.getInstance().sendToServer(req2);
        //the server gives error code 1 is user is following username
        isFollowing = serverResponse2.getErrorCode();
        tweets.clear();
        for (Tree<Tweet> parentTree : tweetGraph) {
            tweets.add(parentTree);
        }
        tweetListView.setItems(tweets);
        tweetListView.setCellFactory(
                new Callback<ListView<Tree<Tweet>>, ListCell<Tree<Tweet>>>() {
                    @Override
                    public ListCell<Tree<Tweet>> call(ListView<Tree<Tweet>> listView) {
                        return new TweetController();
                    }
                }
        );
    }

    /**
     * get data from another class
     *
     * @param data data is a string that is the username of the profile being currently viewed
     */
    @Override
    public void getData(Object data) {
        username = (String) data;
    }

    @FXML
    void onFollow(ActionEvent event) {
        if (isFollowing == 1) {
            Parameter param = new GetProfileParameter(username);
            Request req = new Request("Unfollow", "Unfollows specified user", param);
            Response serverResponse = UIConnectionService.getInstance().sendToServer(req);
            if (serverResponse.getErrorCode() != 0) {
                System.out.println("Error: Could not unfollow user");
            }
            followButton.setText("Follow");
        } else {
            Parameter param = new GetProfileParameter(username);
            Request req = new Request("Follow", "Follows specified user", param);
            Response serverResponse = UIConnectionService.getInstance().sendToServer(req);
            if (serverResponse.getErrorCode() != 0) {
                System.out.println("Error: Could not follow user");
            }
            followButton.setText("Unfollow");
        }
    }
}
