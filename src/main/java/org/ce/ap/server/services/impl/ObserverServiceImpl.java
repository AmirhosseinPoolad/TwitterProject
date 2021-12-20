package main.java.org.ce.ap.server.services.impl;

import main.java.org.ce.ap.server.entity.Tweet;
import main.java.org.ce.ap.server.entity.TweetGraph;
import main.java.org.ce.ap.server.entity.User;
import main.java.org.ce.ap.server.observer.Observer;
import main.java.org.ce.ap.server.services.ObserverService;
import main.java.org.ce.ap.server.util.Tree;

import java.util.ArrayList;
import java.util.HashMap;

public class ObserverServiceImpl extends Observer implements ObserverService {
    //map of following users to their tweets
    private HashMap<String, ArrayList<Tree<Tweet>>> followersMap;

    private static ObserverServiceImpl INSTANCE = null;

    public static synchronized ObserverServiceImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ObserverServiceImpl();
        }
        return INSTANCE;
    }

    /**
     * constructs a new observer service. iterates through all top level tweets and adds them to the map
     */
    private ObserverServiceImpl() {
        TweetGraph graph = TweetGraph.getInstance();
        followersMap = new HashMap<String, ArrayList<Tree<Tweet>>>();
        for (Tree<Tweet> tweet : graph.getTweetTree()) {
            if (!followersMap.containsKey(tweet.getData().getPoster())) {
                followersMap.put(tweet.getData().getPoster(), new ArrayList<Tree<Tweet>>());
            }
            followersMap.get(tweet.getData().getPoster()).add(tweet);
        }
        System.out.println();
    }

    /**
     * updates the map and adds the tweet to the map if it's a top level tweet.
     *
     * @param tweet tweet to be added
     */
    @Override
    public void update(Tree<Tweet> tweet) {
        if (tweet.getParent() == null)
            followersMap.get(tweet.getData().getPoster()).add(tweet);
    }

    /**
     * adds user2 to user1 followers and user1 to user2 followings and updates files/model/users/users.txt
     *
     * @param user1 User to be followed
     * @param user2 User that is following
     */
    @Override
    public void follow(User user1, User user2) {
        user2.addFollowing(user1.getUsername());
        user1.addFollower(user2.getUsername());
        AuthenticatorServiceImpl.getInstance().save();
    }

    /**
     * removes user2 from user1 followers and user1 from user2 followings and updates files/model/users/users.txt
     *
     * @param user1 User to be unfollowed
     * @param user2 User that is unfollowing
     */
    @Override
    public void unfollow(User user1, User user2) {
        user2.removeFollowing(user1.getUsername());
        user1.removeFollower(user2.getUsername());
        AuthenticatorServiceImpl.getInstance().save();
    }

    /**
     * gets all tweets from user
     *
     * @param user User to get tweets of
     * @return arraylist containing top level tweets from user
     */
    @Override
    public ArrayList<Tree<Tweet>> getUserTweets(User user) {
        return followersMap.get(user.getUsername());
    }

    @Override
    public ArrayList<Tree<Tweet>> getUserTweets(String username) {
        return followersMap.get(username);
    }
}
