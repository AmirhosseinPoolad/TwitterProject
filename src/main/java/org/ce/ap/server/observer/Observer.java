package main.java.org.ce.ap.server.observer;

import main.java.org.ce.ap.server.Tweet;

public abstract class Observer {
    public abstract void update(Tweet tweet);
}