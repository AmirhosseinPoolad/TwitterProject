package main.java.org.ce.ap.client.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import main.java.org.ce.ap.client.MenuStatus;
import main.java.org.ce.ap.client.services.impl.UIConnectionService;
import main.java.org.ce.ap.server.entity.Tweet;
import main.java.org.ce.ap.server.jsonHandling.Parameter;
import main.java.org.ce.ap.server.jsonHandling.Request;
import main.java.org.ce.ap.server.jsonHandling.Response;
import main.java.org.ce.ap.server.jsonHandling.impl.parameter.LikeTweetParameter;
import main.java.org.ce.ap.server.util.Tree;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class TweetController extends ListCell<Tree<Tweet>> {

    @FXML
    private VBox box;

    @FXML
    private Text usernameText;

    @FXML
    private Text dateText;

    @FXML
    private Text tweetContentText;

    @FXML
    private Text likesText;

    @FXML
    private Text retweetsText;

    @FXML
    private ListView<Tree<Tweet>> repliesListView;

    private final ObservableList<Tree<Tweet>> replies = FXCollections.observableArrayList();

    private FXMLLoader mLLoader;

    private Tweet tweet;
    private Tree<Tweet> tweetTree;


    private void showReplies() {
        for (Tree<Tweet> parentTree : tweetTree.getLeaves()) {
            replies.add(parentTree);
        }
        if (replies.size() != 0) {
            repliesListView.getItems().clear();
            repliesListView.setItems(replies);
            repliesListView.setCellFactory(
                    new Callback<ListView<Tree<Tweet>>, ListCell<Tree<Tweet>>>() {
                        @Override
                        public ListCell<Tree<Tweet>> call(ListView<Tree<Tweet>> listView) {
                            return new TweetController();
                        }
                    }
            );
        }
    }

    @FXML
    void onLike(ActionEvent event) {
        Parameter param = new LikeTweetParameter(tweet.getTweetId());
        Request req = new Request("LikeTweet", "Likes tweet with given ID", param);
        Response serverResponse = UIConnectionService.getInstance().sendToServer(req);
        if (serverResponse.getErrorCode() != 0) {
            System.err.println("Error: " + serverResponse.getErrorCode());
        } else {
            likesText.setText((tweet.getLikedUsers().size() + 1) + " likes");
        }
    }

    @FXML
    void onReply(ActionEvent event) {
        //TODO
    }

    @FXML
    void onRetweet(ActionEvent event) {
        Parameter param = new LikeTweetParameter(tweet.getTweetId());
        Request req = new Request("RetweetTweet", "Retweets tweet with given ID", param);
        Response serverResponse = UIConnectionService.getInstance().sendToServer(req);
        if (serverResponse.getErrorCode() != 0) {
            System.err.println("Error: " + serverResponse.getErrorCode());
        } else {
            retweetsText.setText((tweet.getLikedUsers().size() + 1) + " retweets");
        }
    }

    @FXML
    void onViewProfile(ActionEvent event) {

    }


    protected void updateItem(Tree<Tweet> item, boolean empty) {
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
            tweet = item.getData();
            tweetTree = item;
            usernameText.setText(tweet.getPoster());
            dateText.setText(tweet.getPostTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
            tweetContentText.setText(tweet.getContent());
            likesText.setText(tweet.getLikedUsers().size() + " likes");
            retweetsText.setText(tweet.getRetweetedUsers().size() + " retweets");
            setGraphic(box); // attach custom layout to ListView cell
            showReplies();
        }
    }
}
