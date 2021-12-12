package main.java.org.ce.ap.server.observer;

import main.java.org.ce.ap.server.Tweet;
import main.java.org.ce.ap.server.util.Tree;

import java.util.ArrayList;

public class Subject {

    private ArrayList<Observer> observerCollection = new ArrayList<Observer>();

    public void registerObserver(Observer observer) {
        observerCollection.add(observer);
    }

    public void unregisterObserver(Observer observer) {
        observerCollection.remove(observer);
    }

    public void notifyObservers(Tree<Tweet> Tweet) {
        for (Observer observer : observerCollection) {
            observer.update(Tweet);
        }
    }

}
