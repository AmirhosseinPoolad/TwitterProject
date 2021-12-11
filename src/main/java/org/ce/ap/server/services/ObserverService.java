package main.java.org.ce.ap.server.services;

import main.java.org.ce.ap.server.Tweet;
import main.java.org.ce.ap.server.User;

import java.util.ArrayList;

/**
 * Handles following and unfollowing other users and viewing a single users tweets. Exists per session/user.
 */
public interface ObserverService {
    /**
     * Adds user to follower list
     * @param user User to be followed
     */
    void follow(User user);

    /**
     * Removes user from follower list
     * @param user User to be unfollowed
     */
    void unfollow(User user);

    /**
     * Gets all tweets from users and returns them in an ArrayList
     * @param user User to get tweets of
     * @return All tweets from user
     */
    ArrayList<Tweet> getUserTweets(User user);

}
