package main.java.org.ce.ap.server.impl;

import main.java.org.ce.ap.server.Tweet;
import main.java.org.ce.ap.server.User;
import main.java.org.ce.ap.server.util.Tree;

public class TweetingServiceImpl {
    //the user that's using the service
    User user;

    /**
     * adds a new tweet to the tweet graph
     *
     * @param content content of the tweet to be added
     * @param parent  set this to the parent tweet if it's a reply, null if it's not a reply
     */
    public void addTweet(String content, Tree<Tweet> parent) {
        //TODO: Implement a singleton to hold the tweet graph
        Tweet tweet = new Tweet(user.getUsername(), content);
        Tree<Tweet> newSubTree = new Tree<>(tweet);
        if (parent != null) {
            parent.addChild(newSubTree);
        }
    }

    /**
     * like the tweet
     *
     * @param tweet tweet to be liked
     */
    public void likeTweet(Tweet tweet) {
        tweet.addLike(user.getUsername());
    }

    /**
     * dislikes the tweet
     *
     * @param tweet tweet to be disliked
     */
    public void dislikeTweet(Tweet tweet) {
        tweet.removeLike(user.getUsername());
    }
}
