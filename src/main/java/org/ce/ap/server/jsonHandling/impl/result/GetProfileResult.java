package main.java.org.ce.ap.server.jsonHandling.impl.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import main.java.org.ce.ap.server.entity.Tweet;
import main.java.org.ce.ap.server.entity.User;
import main.java.org.ce.ap.server.jsonHandling.Result;
import main.java.org.ce.ap.server.util.Tree;

import java.util.ArrayList;

/**
 * results containing a user and list of tweets
 */
public class GetProfileResult extends Result {
    private User user;
    private ArrayList<Tree<Tweet>> tweets;

    public GetProfileResult(@JsonProperty("user") User user,@JsonProperty("tweets") ArrayList<Tree<Tweet>> tweets) {
        this.user = user;
        this.tweets = tweets;
    }

    public User getUser() {
        return user;
    }

    public ArrayList<Tree<Tweet>> getTweets() {
        return tweets;
    }
}
