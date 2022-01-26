package main.java.org.ce.ap.client.controllers;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import main.java.org.ce.ap.client.MenuStatus;
import main.java.org.ce.ap.client.services.impl.SceneHandlerImpl;
import main.java.org.ce.ap.client.services.impl.UIConnectionService;
import main.java.org.ce.ap.server.entity.Tweet;
import main.java.org.ce.ap.server.jsonHandling.Request;
import main.java.org.ce.ap.server.jsonHandling.Response;
import main.java.org.ce.ap.server.jsonHandling.impl.result.GetTimelineResult;
import main.java.org.ce.ap.server.util.Tree;

import java.util.ArrayList;

public class TimelineController {

    private ArrayList<Tree<Tweet>> tweetGraph;

    @FXML
    private ListView<Tree<Tweet>> tweetListView;

    private final ObservableList<Tree<Tweet>> tweets = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        getTimeline();
    }

    @FXML
    void onAbout(ActionEvent event) {
        SceneHandlerImpl.getInstance().newWindow("/about-page.fxml", "About");
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
        UIConnectionService.getInstance().destroyInstance();
        SceneHandlerImpl.getInstance().changeScene("/login-page.fxml");
    }

    @FXML
    void onNewTweet(ActionEvent event) {
        SceneHandlerImpl.getInstance().newWindow("/new-tweet-page.fxml", "New Tweet");
    }

    @FXML
    void onOptions(ActionEvent event) {
        //TODO: OPTIONS PAGE
        //TODO: System Tray??????
    }

    @FXML
    void onRefresh(ActionEvent event) {
        getTimeline();
    }

    @FXML
    void onViewProfile(ActionEvent event) {
        //TODO: PROFILE PAGE
    }

    private void getTimeline() {
        Request req = new Request("GetTimeline", "Get all of the timeline from the server", null);
        Response serverResponse = UIConnectionService.getInstance().sendToServer(req);
        if (serverResponse.getErrorCode() != 0) {
            System.err.println("Error, please try again");
            return;
        }
        GetTimelineResult res = (GetTimelineResult) serverResponse.getResults();
        this.tweetGraph = res.getTimeline();
        for (Tree<Tweet> parentTree : tweetGraph) {
            tweets.add(parentTree);
        }
        tweetListView.getItems().clear();
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

}