package main.java.org.ce.ap.server.observer;

import main.java.org.ce.ap.server.Tweet;
import main.java.org.ce.ap.server.util.Tree;

public abstract class Observer {
    public abstract void update(Tree<Tweet> tweet);
}