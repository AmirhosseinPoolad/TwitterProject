package main.java.org.ce.ap.client.services;

import main.java.org.ce.ap.server.entity.Tweet;
import main.java.org.ce.ap.server.entity.User;
import main.java.org.ce.ap.server.util.Tree;

import java.util.ArrayList;

public interface ConsoleViewService {
    /**
     * renders an arraylist of tweet trees
     *
     * @param tweetTree list of tweet trees to render
     */
    void showTweetTree(ArrayList<Tree<Tweet>> tweetTree);

    /**
     * renders a list of users
     *
     * @param userList list of users
     */
    void showUserList(ArrayList<String> userList);

    /**
     * renders a user's information
     *
     * @param user requested user
     */
    void showUserInfo(User user);
}
