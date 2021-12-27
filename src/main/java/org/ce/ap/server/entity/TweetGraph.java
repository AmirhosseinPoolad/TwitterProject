package main.java.org.ce.ap.server.entity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.java.org.ce.ap.server.jsonHandling.MapperSingleton;
import main.java.org.ce.ap.server.services.impl.PropertyServiceImpl;
import main.java.org.ce.ap.server.util.Tree;
import main.java.org.ce.ap.server.util.TreeIterator;

import java.io.*;
import java.util.ArrayList;

/**
 * Singleton used to store all of the tweets in a tree data structure
 */
public class TweetGraph {
    private static TweetGraph INSTANCE = null;
    //list of top level tweets
    public ArrayList<Tree<Tweet>> tweetTree;

    private int tweetCount;

    public static synchronized TweetGraph getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TweetGraph();
        }
        return INSTANCE;
    }

    private TweetGraph() {
        tweetTree = new ArrayList<>();
        this.read();
        this.tweetCount = countTweets();
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

    /**
     * searchs for the subtree that has tweet as head
     *
     * @param tweetId tweetId to search for
     * @return subtree that has tweet as head
     */
    public synchronized Tree<Tweet> getTweet(int tweetId) {
        for (Tree<Tweet> treeHead : tweetTree) {
            TreeIterator<Tweet> it = new TreeIterator<>(treeHead);
            while (it.hasNext()) {
                Tree<Tweet> next = it.nextTree();
                if (next.getData().getTweetId() == tweetId) {
                    return next;
                }
            }
        }
        return null;
    }

    public ArrayList<Tree<Tweet>> getTweetTree() {
        return tweetTree;
    }

    /**
     * adds a tweet to the tweet graph.
     *
     * @param tweet  tweet to be added
     * @param parent parent of the tweet as a tree
     * @return added tweet, as a tree/subtree in the tweet graph
     */
    public synchronized Tree<Tweet> addTweet(Tweet tweet, Tree<Tweet> parent) {
        Tree<Tweet> newTree = new Tree<>(tweet);
        if (parent == null) {
            tweetTree.add(newTree);
        } else {
            parent.addChild(newTree);
            newTree.setParent(parent);
        }
        save();
        this.tweetCount++;
        return newTree;
    }

    /**
     * adds a tweet to the tweet graph.
     *
     * @param tweet  tweet to be added
     * @param parent parent of the tree
     */
    public synchronized void addTweet(Tweet tweet, Tweet parent) {
        Tree<Tweet> parentTree = getTweet(parent);
        addTweet(tweet, parentTree);
    }

    /**
     * serializes and saves the tweet graph to files/model/tweets/tweetGraph.txt
     */
    public synchronized void save() {
        String tweetsFile = PropertyServiceImpl.getInstance().getProperty("server.tweets.file") + "/tweetGraph.txt";
        try (OutputStream outputStream = new FileOutputStream(tweetsFile)) {
            MapperSingleton.getObjectMapper().writeValue(outputStream, this.tweetTree);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * parses and reconstructs the tweet graph from files/model/tweets/tweetGraph.txt
     */
    public synchronized void read() {
        String tweetsFile = PropertyServiceImpl.getInstance().getProperty("server.tweets.file") + "/tweetGraph.txt";
        try (InputStream in = new FileInputStream(tweetsFile)) {
            byte[] buffer = new byte[1 << 20]; //1 megabyte buffer
            String resString;
            int read = in.read(buffer);
            if (read != -1) {
                resString = new String(buffer, 0, read);
                ObjectMapper mapper = MapperSingleton.getObjectMapper();
                ArrayList<Tree<Tweet>> tree = mapper.readValue(resString, new TypeReference<ArrayList<Tree<Tweet>>>() {
                });
                this.tweetTree = tree;
                this.tweetCount = countTweets();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getTweetCount() {
        return tweetCount;
    }

    private int countTweets() {
        int count = 0;
        for (Tree<Tweet> treeHead : tweetTree) {
            TreeIterator<Tweet> it = new TreeIterator<>(treeHead);
            while (it.hasNext()) {
                Tree<Tweet> next = it.nextTree();
                count++;
            }
        }
        return count;
    }
}
