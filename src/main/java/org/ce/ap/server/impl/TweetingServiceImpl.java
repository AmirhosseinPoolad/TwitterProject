package main.java.org.ce.ap.server.impl;

import main.java.org.ce.ap.server.entity.Tweet;
import main.java.org.ce.ap.server.entity.TweetGraph;
import main.java.org.ce.ap.server.entity.User;
import main.java.org.ce.ap.server.services.TweetingService;
import main.java.org.ce.ap.server.util.Tree;

public class TweetingServiceImpl implements TweetingService {
    //the user that's using the service
    User user;
    //observer service that is notified of any new tweets
    ObserverServiceImpl observerService;

    /**
     * constructs a new tweeting service for user
     *
     * @param user user that's using the service
     */
    public TweetingServiceImpl(User user) {
        this.user = user;
        this.observerService = ObserverServiceImpl.getInstance();
    }

    /**
     * adds a new tweet to the tweet graph
     *
     * @param content content of the tweet to be added
     * @param parent  set this to the parent tweet if it's a reply, null if it's not a reply
     */
    public void addTweet(String content, Tree<Tweet> parent) {
        Tweet tweet = new Tweet(user.getUsername(), content, TweetGraph.getInstance().getTweetCount());
        Tree<Tweet> newTree = TweetGraph.getInstance().addTweet(tweet, parent);
        observerService.update(newTree);
    }

    @Override
    public void addTweet(String content, int parentId) {

    }

    /**
     * like the tweet
     *
     * @param tweet tweet to be liked
     */
    public void likeTweet(Tweet tweet) {
        tweet.addLike(user.getUsername());
    }

    @Override
    public void likeTweet(int tweetId) {
        TweetGraph.getInstance().getTweet(tweetId).getData().addLike(user.getUsername());
    }

    /**
     * dislikes the tweet
     *
     * @param tweet tweet to be disliked
     */
    public void dislikeTweet(Tweet tweet) {
        tweet.removeLike(user.getUsername());
    }

    @Override
    public void dislikeTweet(int tweetId) {
        TweetGraph.getInstance().getTweet(tweetId).getData().removeLike(user.getUsername());
    }

    /**
     * retweets the tweet
     *
     * @param tweet tweet to be retweeted
     */
    public void retweetTweet(Tweet tweet) {
        tweet.addRetweet(user.getUsername());
    }

    @Override
    public void retweetTweet(int tweetId) {
        TweetGraph.getInstance().getTweet(tweetId).getData().addRetweet(user.getUsername());
    }

    /**
     * removes retweet from tweet
     *
     * @param tweet tweet to unretweet
     */
    public void unretweetTweet(Tweet tweet) {
        tweet.removeRetweet(user.getUsername());
    }

    @Override
    public void unretweetTweet(int tweetId) {
        TweetGraph.getInstance().getTweet(tweetId).getData().removeRetweet(user.getUsername());
    }

}
