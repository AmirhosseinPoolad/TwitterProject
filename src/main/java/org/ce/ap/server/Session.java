package main.java.org.ce.ap.server;

import main.java.org.ce.ap.server.entity.User;
import main.java.org.ce.ap.server.impl.TimelineServiceImpl;
import main.java.org.ce.ap.server.impl.TweetingServiceImpl;
import main.java.org.ce.ap.server.services.TimelineService;
import main.java.org.ce.ap.server.services.TweetingService;

import java.net.Socket;

public class Session implements Runnable {
    private User user;
    private Socket connectionSocket;
    private TimelineService timelineService;
    private TweetingService tweetingService;

    public Session(Socket connectionSocket, User user) {
        this.connectionSocket = connectionSocket;
        this.user = user;
        this.timelineService = new TimelineServiceImpl(user);
        this.tweetingService = new TweetingServiceImpl(user);
    }

    @Override
    public void run() {
        user.printInfo();
    }
}
