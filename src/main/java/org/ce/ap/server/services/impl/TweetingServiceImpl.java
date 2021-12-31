package main.java.org.ce.ap.server.services.impl;

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
    public Tree<Tweet> addTweet(String content, Tree<Tweet> parent) throws IllegalArgumentException {
        if (content == null || content.equals(""))
            throw new IllegalArgumentException("Content empty");
        Tweet tweet = new Tweet(user.getUsername(), content, TweetGraph.getInstance().getTweetCount());
        Tree<Tweet> newTree = TweetGraph.getInstance().addTweet(tweet, parent);
        observerService.update(newTree);
        return newTree;
    }

    /**
     * adds a new tweet to the tweet graph
     *
     * @param content  content of the tweet to be added
     * @param parentId set this to the parent tweet ID if it's a reply, -1 if it's not a reply
     */
    @Override
    public Tree<Tweet> addTweet(String content, int parentId) throws IllegalArgumentException {
        if (parentId == -1) {
            return addTweet(content, null);
        }
        return addTweet(content, TweetGraph.getInstance().getTweet(parentId));
    }

    /**
     * like the tweet
     *
     * @param tweet tweet to be liked
     */
    public Tree<Tweet> likeTweet(Tweet tweet) {
        tweet.addLike(user.getUsername());
        return TweetGraph.getInstance().getTweet(tweet);
    }

    /**
     * like the tweet
     *
     * @param tweetId Id of the tweet to be liked
     */
    @Override
    public Tree<Tweet> likeTweet(int tweetId) throws IllegalArgumentException {
        TweetGraph.getInstance().getTweet(tweetId).getData().addLike(user.getUsername());
        return TweetGraph.getInstance().getTweet(tweetId);
    }

    /**
     * dislikes the tweet
     *
     * @param tweet tweet to be disliked
     */
    public Tree<Tweet> dislikeTweet(Tweet tweet) {
        tweet.removeLike(user.getUsername());
        return TweetGraph.getInstance().getTweet(tweet);
    }

    /**
     * dislikes the tweet
     *
     * @param tweetId ID of the tweet to be retweeted
     */
    @Override
    public Tree<Tweet> dislikeTweet(int tweetId) throws IllegalArgumentException {
        TweetGraph.getInstance().getTweet(tweetId).getData().removeLike(user.getUsername());
        return TweetGraph.getInstance().getTweet(tweetId);
    }

    /**
     * retweets the tweet
     *
     * @param tweet tweet to be retweeted
     */
    public Tree<Tweet> retweetTweet(Tweet tweet) {
        tweet.addRetweet(user.getUsername());
        return TweetGraph.getInstance().getTweet(tweet);
    }

    /**
     * retweets the tweet
     *
     * @param tweetId tweet to be retweeted
     */
    @Override
    public Tree<Tweet> retweetTweet(int tweetId) throws IllegalArgumentException {
        TweetGraph.getInstance().getTweet(tweetId).getData().addRetweet(user.getUsername());
        return TweetGraph.getInstance().getTweet(tweetId);
    }

    /**
     * removes retweet from tweet
     *
     * @param tweet tweet to unretweet
     */
    public Tree<Tweet> unretweetTweet(Tweet tweet) {
        tweet.removeRetweet(user.getUsername());
        return TweetGraph.getInstance().getTweet(tweet);
    }

    /**
     * removes retweet from tweet
     *
     * @param tweetId tweet to unretweet
     */
    @Override
    public Tree<Tweet> unretweetTweet(int tweetId) throws IllegalArgumentException {
        TweetGraph.getInstance().getTweet(tweetId).getData().removeRetweet(user.getUsername());
        return TweetGraph.getInstance().getTweet(tweetId);
    }

}
