package main.java.org.ce.ap.server.services;

import main.java.org.ce.ap.server.entity.Tweet;
import main.java.org.ce.ap.server.entity.User;
import main.java.org.ce.ap.server.util.Tree;

import java.util.ArrayList;

/**
 * Handles the timeline. Exists per user.
 */
public interface TimelineService {
    /**
     * Gets all of the tweets from all following users and returns them in an ArrayList.
     *
     * @param user User to get timeline of
     * @return Timeline of user
     */
    ArrayList<Tree<Tweet>> getTimeline(User user);
}
