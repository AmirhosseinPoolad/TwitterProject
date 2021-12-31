package main.java.org.ce.ap.server.services;

import main.java.org.ce.ap.server.entity.Tweet;
import main.java.org.ce.ap.server.util.Tree;

public interface TweetingService {
    /**
     * adds a new tweet to the tweet graph
     *
     * @param content content of the tweet to be added
     * @param parent  set this to the parent tweet if it's a reply, null if it's not a reply
     */
    public Tree<Tweet> addTweet(String content, Tree<Tweet> parent);

    /**
     * adds a new tweet to the tweet graph
     *
     * @param content content of the tweet to be added
     * @param parentId  set this to the parent tweet ID if it's a reply, -1 if it's not a reply
     */
    public Tree<Tweet> addTweet(String content, int parentId);

    /**
     * like the tweet
     *
     * @param tweet tweet to be liked
     */
    public Tree<Tweet> likeTweet(Tweet tweet);

    /**
     * like the tweet
     *
     * @param tweetId Id of the tweet to be liked
     */
    public Tree<Tweet> likeTweet(int tweetId);

    /**
     * dislikes the tweet
     *
     * @param tweet tweet to be disliked
     */
    public Tree<Tweet> dislikeTweet(Tweet tweet);

    /**
     * dislikes the tweet
     *
     * @param tweetId ID of the tweet to be retweeted
     */
    public Tree<Tweet> dislikeTweet(int tweetId);

    /**
     * retweets the tweet
     *
     * @param tweet tweet to be retweeted
     */
    public Tree<Tweet> retweetTweet(Tweet tweet);

    /**
     * retweets the tweet
     *
     * @param tweetId tweet to be retweeted
     */
    public Tree<Tweet> retweetTweet(int tweetId);

    /**
     * removes retweet from tweet
     *
     * @param tweet tweet to unretweet
     */
    public Tree<Tweet> unretweetTweet(Tweet tweet);

    /**
     * removes retweet from tweet
     *
     * @param tweetId tweet to unretweet
     */
    public Tree<Tweet> unretweetTweet(int tweetId);
}
