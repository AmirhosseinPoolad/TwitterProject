package main.java.org.ce.ap.server.services;

import main.java.org.ce.ap.server.entity.Tweet;
import main.java.org.ce.ap.server.entity.User;
import main.java.org.ce.ap.server.util.Tree;

import java.util.ArrayList;

/**
 * Handles following and unfollowing other users and viewing a single users tweets. Exists per server.
 */
public interface ObserverService {
    /**
     * Adds user1 to follower list of user2
     * @param user1 User to be followed
     * @param user2 User that is following
     */
    void follow(User user1, User user2);

    /**
     * Removes user1 from follower list of user2
     * @param user1 User to be unfollowed
     * @param user2 User that is unfollowing
     */
    void unfollow(User user1, User user2);

    /**
     * Gets all tweets from users and returns them in an ArrayList
     * @param user User to get tweets of
     * @return All tweets from user
     */
    ArrayList<Tree<Tweet>> getUserTweets(User user);

}
