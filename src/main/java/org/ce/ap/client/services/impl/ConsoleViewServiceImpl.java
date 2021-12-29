package main.java.org.ce.ap.client.services.impl;

import main.java.org.ce.ap.client.services.ConsoleViewService;
import main.java.org.ce.ap.server.entity.Tweet;
import main.java.org.ce.ap.server.entity.User;
import main.java.org.ce.ap.server.util.Tree;
import main.java.org.ce.ap.server.util.TreeIterator;

import java.time.format.FormatStyle;
import java.util.ArrayList;

import static java.time.format.DateTimeFormatter.ofLocalizedDateTime;

public class ConsoleViewServiceImpl implements ConsoleViewService {

    @Override
    public void showTweetTree(ArrayList<Tree<Tweet>> tweetTree) {
        if (tweetTree == null)
            return;
        for (Tree<Tweet> tree : tweetTree) {
            TreeIterator it = new TreeIterator<Tweet>(tree);
            while (it.hasNext()) {
                int depth = it.getNextDepth();
                Tweet next = (Tweet) it.next();

                printDepthWhiteSpace(depth);
                System.out.println(next.getPoster() + " | ID: " + next.getTweetId() + " | " + next.getPostTime().format(ofLocalizedDateTime(FormatStyle.MEDIUM)));

                printDepthWhiteSpace(depth);
                System.out.println(next.getContent());

                printDepthWhiteSpace(depth);
                System.out.print("Liked By: ");
                for (String likedUser : next.getLikedUsers()) {
                    System.out.print(likedUser);
                    System.out.print(" | ");
                }
                System.out.print("Retweeted By: ");
                for (String retweetedUsed : next.getRetweetedUsers()) {
                    System.out.print(retweetedUsed);
                    System.out.print(" | ");
                }
                System.out.println();
                System.out.println("----------------------------------");
            }
        }
    }

    @Override
    public void showUserList(ArrayList<String> userList) {
        if (userList == null)
            return;
        for (String user : userList) {
            System.out.println(user);
        }
    }

    @Override
    public void showUserInfo(User user) {
        user.printInfo();
    }

    /**
     * prints depth*4 spaces
     */
    private void printDepthWhiteSpace(int depth) {
        for (int i = 0; i < depth; i++)
            System.out.print("----");
    }
}
