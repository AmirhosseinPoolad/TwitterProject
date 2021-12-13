package main.java.org.ce.ap.server;

import main.java.org.ce.ap.server.util.Tree;
import main.java.org.ce.ap.server.util.TreeIO;
import main.java.org.ce.ap.server.util.TreeIterator;

import java.io.*;
import java.util.ArrayList;

/**
 * Singleton used to store all of the tweets in a tree data structure
 */
public class TweetGraph {
    private static TweetGraph INSTANCE = null;
    public ArrayList<Tree<Tweet>> tweetTree;

    public static synchronized TweetGraph getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TweetGraph();
        }
        return INSTANCE;
    }

    private TweetGraph() {
        tweetTree = new ArrayList<>();
        this.read();
    }

    /**
     * searchs for the subtree that has tweet as head
     *
     * @param tweet tweet to search for
     * @return subtree that has tweet as head
     */
    public synchronized Tree<Tweet> getTweet(Tweet tweet) {
        for (Tree<Tweet> treeHead : tweetTree) {
            TreeIterator<Tweet> it = new TreeIterator<>(treeHead);
            while (it.hasNext()) {
                Tree<Tweet> next = it.nextTree();
                if (next.getData().equals(tweet)) {
                    return next;
                }
            }
        }
        return null;
    }

    public ArrayList<Tree<Tweet>> getTweetTree() {
        return tweetTree;
    }

    public synchronized Tree<Tweet> addTweet(Tweet tweet, Tree<Tweet> parent) {
        Tree<Tweet> newTree = new Tree<>(tweet);
        if (parent == null) {
            tweetTree.add(newTree);
        } else {
            parent.addChild(newTree);
            newTree.setParent(parent);
        }
        save();
        return newTree;
    }

    public synchronized void addTweet(Tweet tweet, Tweet parent) {
        Tree<Tweet> parentTree = getTweet(parent);
        addTweet(tweet, parentTree);
    }

    public synchronized void save() {
        try (BufferedWriter outputStream = new BufferedWriter(new FileWriter("files/model/tweets/tweetGraph.txt"))) {
            TreeIO<Tweet> tweetTreeIO = new TreeIO<>();
            for (Tree<Tweet> subTree : tweetTree) {
                tweetTreeIO.writeTree(subTree, outputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void read() {
        BufferedReader inputStream = null;
        try (BufferedReader in = new BufferedReader(new FileReader("files/model/tweets/tweetGraph.txt"))) {
            TreeIO<Tweet> tweetTreeIO = new TreeIO<>();
            String firstLine;
            do {
                firstLine = in.readLine();
                if (firstLine == null)
                    break;
                tweetTree.add(tweetTreeIO.readTree(in, firstLine));
            } while (firstLine != null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}