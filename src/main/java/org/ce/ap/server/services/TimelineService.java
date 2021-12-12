package main.java.org.ce.ap.server.services;

import main.java.org.ce.ap.server.Tweet;
import main.java.org.ce.ap.server.User;

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
    ArrayList<Tweet> getTimeline(User user);
}
