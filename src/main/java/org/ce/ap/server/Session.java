package main.java.org.ce.ap.server;

import main.java.org.ce.ap.server.entity.User;
import main.java.org.ce.ap.server.impl.AuthenticatorServiceImpl;
import main.java.org.ce.ap.server.impl.ObserverServiceImpl;
import main.java.org.ce.ap.server.impl.TimelineServiceImpl;
import main.java.org.ce.ap.server.impl.TweetingServiceImpl;
import main.java.org.ce.ap.server.jsonHandling.MapperSingleton;
import main.java.org.ce.ap.server.jsonHandling.Request;
import main.java.org.ce.ap.server.jsonHandling.impl.*;
import main.java.org.ce.ap.server.services.TimelineService;
import main.java.org.ce.ap.server.services.TweetingService;

import java.io.*;
import java.net.Socket;

public class Session implements Runnable {
    private User user = null;
    private Socket connectionSocket;
    private OutputStream out;
    private InputStream in;
    private TimelineService timelineService;
    private TweetingService tweetingService;

    public Session(Socket connectionSocket) {
        this.connectionSocket = connectionSocket;
        try {
            out = connectionSocket.getOutputStream();
            in = connectionSocket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int signIn(String username, String password) {
        this.user = AuthenticatorServiceImpl.getInstance().logIn(username, password);
        if (user != null) {
            this.timelineService = new TimelineServiceImpl(user);
            this.tweetingService = new TweetingServiceImpl(user);
            return 1;
        }
        return 0;
    }

    @Override
    public void run() {
        try {
            byte[] buffer = new byte[1 << 20]; //1 megabyte buffer
            String readString;
            while (true) {
                int read = in.read(buffer);
                readString = new String(buffer, 0, read);
                Request req = MapperSingleton.getObjectMapper().readValue(readString, Request.class);
                if (req.getMethod().equals("Register")) {
                    RegisterParameter param = (RegisterParameter) req.getParameterValues();
                    User res = AuthenticatorServiceImpl.getInstance().signUp(param.getUsername(), param.getPassword(),
                            param.getFirstName(), param.getLastName(), param.getBiography(), param.getBirthdayDate());
                    if (res == null) {
                        //TODO:RESPONSE ERROR
                        //TODO:LOG ERROR
                    } else {
                        signIn(user.getUsername(), param.getPassword());
                        //TODO: RESPONSE
                        //TODO: LOG
                        System.out.println("REGISTERED AND SIGNED IN");
                        signedInRun();
                        return;
                    }
                } else if (req.getMethod().equals("SignIn")) {
                    SignInParameter param = (SignInParameter) req.getParameterValues();
                    int res = signIn(param.getUsername(), param.getPassword());
                    if (res == 0) {
                        //TODO:RESPONSE ERROR
                        //TODO:LOG ERROR
                    } else {
                        signIn(user.getUsername(), param.getPassword());
                        //TODO: RESPONSE
                        //TODO: LOG
                        System.out.println("SIGNED IN");
                        signedInRun();
                        return;
                    }
                } else if (req.getMethod().equals("Quit")) {
                    //TODO: RESPONSE
                    //TODO: LOG
                    return;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void signedInRun() {
        try {
            byte[] buffer = new byte[1 << 20]; //1 megabyte buffer
            String readString;
            while (true) {
                int read = in.read(buffer);
                readString = new String(buffer, 0, read);
                Request req = MapperSingleton.getObjectMapper().readValue(readString, Request.class);
                if (req.getMethod().equals("Quit")) {
                    //TODO: RESPONSE
                    //TODO: LOG
                    return;
                } else if (req.getMethod().equals("SendTweet")) {
                    SendTweetParameter param = (SendTweetParameter) req.getParameterValues();
                    tweetingService.addTweet(param.getContent(), param.getParentId());
                    //TODO: RESPONSE
                    //TODO: LOG
                } else if (req.getMethod().equals("LikeTweet")) {
                    LikeTweetParameter param = (LikeTweetParameter) req.getParameterValues();
                    tweetingService.likeTweet(param.getTweetId());
                    //TODO: RESPONSE
                    //TODO: LOG
                } else if (req.getMethod().equals("DislikeTweet")) {
                    LikeTweetParameter param = (LikeTweetParameter) req.getParameterValues();
                    tweetingService.dislikeTweet(param.getTweetId());
                    //TODO: RESPONSE
                    //TODO: LOG
                } else if (req.getMethod().equals("RetweetTweet")) {
                    LikeTweetParameter param = (LikeTweetParameter) req.getParameterValues();
                    tweetingService.retweetTweet(param.getTweetId());
                    //TODO: RESPONSE
                    //TODO: LOG
                } else if (req.getMethod().equals("UnretweetTweet")) {
                    LikeTweetParameter param = (LikeTweetParameter) req.getParameterValues();
                    tweetingService.unretweetTweet(param.getTweetId());
                    //TODO: RESPONSE
                    //TODO: LOG
                } else if (req.getMethod().equals("GetProfile")) {
                    GetProfileParameter param = (GetProfileParameter) req.getParameterValues();
                    ObserverServiceImpl.getInstance().getUserTweets(param.getUsername());
                    //TODO: RESPONSE
                    //TODO: LOG
                } else if (req.getMethod().equals("GetTimeline")) {
                    timelineService.getTimeline();
                    //TODO: RESPONSE
                    //TODO: LOG
                } else if (req.getMethod().equals("Follow")) {
                    GetProfileParameter param = (GetProfileParameter) req.getParameterValues();
                    User user1 = AuthenticatorServiceImpl.getInstance().fromUsername(param.getUsername());
                    User user2 = user;
                    ObserverServiceImpl.getInstance().follow(user1, user2);
                    //TODO: RESPONSE
                    //TODO: LOG
                } else if (req.getMethod().equals("Unfollow")) {
                    GetProfileParameter param = (GetProfileParameter) req.getParameterValues();
                    User user1 = AuthenticatorServiceImpl.getInstance().fromUsername(param.getUsername());
                    User user2 = user;
                    ObserverServiceImpl.getInstance().unfollow(user1, user2);
                    //TODO: RESPONSE
                    //TODO: LOG
                } else if (req.getMethod().equals("GetFolowers")) {
                    user.getFollowers();
                    //TODO: RESPONSE
                    //TODO: LOG
                } else if (req.getMethod().equals("GetFollowings")) {
                    user.getFollowings();
                    //TODO: RESPONSE
                    //TODO: LOG
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
