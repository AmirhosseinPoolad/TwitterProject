package main.java.org.ce.ap.client.controllers;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.Callback;
import main.java.org.ce.ap.client.services.impl.PropertiesServiceImpl;
import main.java.org.ce.ap.client.services.impl.SceneHandlerImpl;
import main.java.org.ce.ap.client.services.impl.UIConnectionService;
import main.java.org.ce.ap.server.entity.Tweet;
import main.java.org.ce.ap.server.entity.User;
import main.java.org.ce.ap.server.jsonHandling.Request;
import main.java.org.ce.ap.server.jsonHandling.Response;
import main.java.org.ce.ap.server.jsonHandling.impl.result.GetTimelineResult;
import main.java.org.ce.ap.server.util.Tree;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TimelineController implements DataGetter {

    //graph of tweets and replies
    private ArrayList<Tree<Tweet>> tweetGraph;
    //current logged in user
    private User user;
    @FXML
    private Text notFoundText;
    @FXML
    private TextField searchField;
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
        String fxml = PropertiesServiceImpl.getInstance().getProperty("client.login.page");
        SceneHandlerImpl.getInstance().changeScene(fxml);
        String path = PropertiesServiceImpl.getInstance().getProperty("client.saved.file");
        try (BufferedWriter out = new BufferedWriter(new FileWriter(path))) {
            out.write(String.valueOf(false));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onNewTweet(ActionEvent event) {
        String fxml = PropertiesServiceImpl.getInstance().getProperty("client.new.tweet.page");
        SceneHandlerImpl.getInstance().newWindow(fxml, "New Tweet");
    }

    @FXML
    void onOptions(ActionEvent event) {
        //TODO: OPTIONS PAGE
        //TODO: System Tray
    }

    @FXML
    void onRefresh(ActionEvent event) {
        getTimeline();
    }

    @FXML
    void onViewProfile(ActionEvent event) {
        String fxml = PropertiesServiceImpl.getInstance().getProperty("client.profile.page");
        SceneHandlerImpl.getInstance().newWindow(fxml, "Profile Page", user.getUsername());
    }

    /**
     * gets the timeline from server and shows it
     */
    private void getTimeline() {
        Request req = new Request("GetTimeline", "Get all of the timeline from the server", null);
        Response serverResponse = UIConnectionService.getInstance().sendToServer(req);
        if (serverResponse.getErrorCode() != 0) {
            System.err.println("Error, please try again");
            return;
        }
        GetTimelineResult res = (GetTimelineResult) serverResponse.getResults();
        this.tweetGraph = res.getTimeline();
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

    @FXML
    void onSearch(ActionEvent event) {
        String fxml = PropertiesServiceImpl.getInstance().getProperty("client.profile.page");
        SceneHandlerImpl.getInstance().newWindow(fxml, "Profile Page", searchField.getText());
    }

    @Override
    public void getData(Object data) {
        user = (User) data;
    }
}