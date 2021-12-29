package main.java.org.ce.ap.client.services;

import main.java.org.ce.ap.server.entity.Tweet;
import main.java.org.ce.ap.server.entity.User;
import main.java.org.ce.ap.server.util.Tree;

import java.util.ArrayList;

public interface ConsoleViewService {
    void showTweetTree(ArrayList<Tree<Tweet>> tweetTree);
    void showUserList(ArrayList<String> userList);
    void showUserInfo(User user);
}
