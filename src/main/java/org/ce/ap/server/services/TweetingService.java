package main.java.org.ce.ap.server.services;

import main.java.org.ce.ap.server.Tweet;
import main.java.org.ce.ap.server.util.Tree;

public interface TweetingService {
    /**
     * adds a new tweet to the tweet graph
     *
     * @param content content of the tweet to be added
     * @param parent  set this to the parent tweet if it's a reply, null if it's not a reply
     */
    public void addTweet(String content, Tree<Tweet> parent);

    /**
     * like the tweet
     *
     * @param tweet tweet to be liked
     */
    public void likeTweet(Tweet tweet);

    /**
     * dislikes the tweet
     *
     * @param tweet tweet to be disliked
     */
    public void dislikeTweet(Tweet tweet);

    /**
     * retweets the tweet
     *
     * @param tweet tweet to be retweeted
     */
    public void retweetTweet(Tweet tweet);

    /**
     * removes retweet from tweet
     *
     * @param tweet tweet to unretweet
     */
    public void unretweetTweet(Tweet tweet);
}
