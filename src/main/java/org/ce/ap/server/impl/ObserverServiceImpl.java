package main.java.org.ce.ap.server.impl;

import main.java.org.ce.ap.server.entity.Tweet;
import main.java.org.ce.ap.server.entity.TweetGraph;
import main.java.org.ce.ap.server.entity.User;
import main.java.org.ce.ap.server.observer.Observer;
import main.java.org.ce.ap.server.services.ObserverService;
import main.java.org.ce.ap.server.util.Tree;

import java.util.ArrayList;
import java.util.HashMap;

public class ObserverServiceImpl extends Observer implements ObserverService {
    HashMap<String, ArrayList<Tree<Tweet>>> followersMap;

    private static ObserverServiceImpl INSTANCE = null;

    public static synchronized ObserverServiceImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ObserverServiceImpl();
        }
        return INSTANCE;
    }

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

    @Override
    public void update(Tree<Tweet> tweet) {
        if (tweet.getParent() == null)
            followersMap.get(tweet.getData().getPoster()).add(tweet);
    }

    @Override
    public void follow(User user1, User user2) {
        user2.addFollowing(user1.getUsername());
        user1.addFollower(user2.getUsername());
    }

    @Override
    public void unfollow(User user1, User user2) {
        user2.removeFollowing(user1.getUsername());
        user1.removeFollower(user2.getUsername());
    }

    @Override
    public ArrayList<Tree<Tweet>> getUserTweets(User user) {
        return followersMap.get(user.getUsername());

    }
}
