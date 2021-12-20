package main.java.org.ce.ap.server;

import main.java.org.ce.ap.server.entity.Tweet;
import main.java.org.ce.ap.server.entity.TweetGraph;
import main.java.org.ce.ap.server.entity.User;
import main.java.org.ce.ap.server.jsonHandling.Response;
import main.java.org.ce.ap.server.jsonHandling.Result;
import main.java.org.ce.ap.server.jsonHandling.impl.result.*;
import main.java.org.ce.ap.server.services.impl.AuthenticatorServiceImpl;
import main.java.org.ce.ap.server.services.impl.ObserverServiceImpl;
import main.java.org.ce.ap.server.services.impl.TimelineServiceImpl;
import main.java.org.ce.ap.server.services.impl.TweetingServiceImpl;
import main.java.org.ce.ap.server.jsonHandling.MapperSingleton;
import main.java.org.ce.ap.server.jsonHandling.Request;
import main.java.org.ce.ap.server.jsonHandling.impl.parameter.*;
import main.java.org.ce.ap.server.services.TimelineService;
import main.java.org.ce.ap.server.services.TweetingService;
import main.java.org.ce.ap.server.util.Tree;
import main.java.org.ce.ap.server.util.TreeIterator;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

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
                        Result result = new UserResult(res);
                        Response response = new Response(true, 1, result);
                        sendResponse(response);
                        //TODO:LOG ERROR
                    } else {
                        signIn(user.getUsername(), param.getPassword());
                        Result result = new UserResult(res);
                        Response response = new Response(false, 0, result);
                        sendResponse(response);
                        //TODO: LOG
                        System.out.println("REGISTERED AND SIGNED IN");
                        signedInRun();
                        return;
                    }
                } else if (req.getMethod().equals("SignIn")) {
                    SignInParameter param = (SignInParameter) req.getParameterValues();
                    int res = signIn(param.getUsername(), param.getPassword());
                    if (res == 0) {
                        Result result = new UserResult(user);
                        Response response = new Response(true, 1, result);
                        sendResponse(response);
                        //TODO:LOG ERROR
                    } else {
                        signIn(user.getUsername(), param.getPassword());
                        Result result = new UserResult(user);
                        Response response = new Response(false, 0, result);
                        sendResponse(response);
                        //TODO: LOG
                        System.out.println("SIGNED IN");
                        signedInRun();
                        return;
                    }
                } else if (req.getMethod().equals("Quit")) {
                    Result result = new EmptyResult();
                    Response response = new Response(false, 0, result);
                    sendResponse(response);
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
                    Result result = new EmptyResult();
                    Response response = new Response(false, 0, result);
                    sendResponse(response);
                    //TODO: LOG
                    return;
                } else if (req.getMethod().equals("SendTweet")) {
                    SendTweetParameter param = (SendTweetParameter) req.getParameterValues();
                    Tree<Tweet> tweetTree = tweetingService.addTweet(param.getContent(), param.getParentId());
                    Tree<Tweet> topTree = tweetTree.getParent();
                    while (topTree.getParent() != null) {
                        topTree = topTree.getParent();
                    }
                    Result result = new TweetResult(topTree);
                    Response response = new Response(false, 0, result);
                    sendResponse(response);
                    //TODO: LOG
                } else if (req.getMethod().equals("LikeTweet")) {
                    LikeTweetParameter param = (LikeTweetParameter) req.getParameterValues();
                    Tree<Tweet> tweetTree = tweetingService.likeTweet(param.getTweetId());
                    Tree<Tweet> topTree = tweetTree.getParent();
                    while (topTree.getParent() != null) {
                        topTree = topTree.getParent();
                    }
                    Result result = new TweetResult(topTree);
                    Response response = new Response(false, 0, result);
                    sendResponse(response);
                    //TODO: LOG
                } else if (req.getMethod().equals("DislikeTweet")) {
                    LikeTweetParameter param = (LikeTweetParameter) req.getParameterValues();
                    Tree<Tweet> tweetTree = tweetingService.dislikeTweet(param.getTweetId());
                    Tree<Tweet> topTree = tweetTree.getParent();
                    while (topTree.getParent() != null) {
                        topTree = topTree.getParent();
                    }
                    Result result = new TweetResult(topTree);
                    Response response = new Response(false, 0, result);
                    sendResponse(response);
                    //TODO: LOG
                } else if (req.getMethod().equals("RetweetTweet")) {
                    LikeTweetParameter param = (LikeTweetParameter) req.getParameterValues();
                    Tree<Tweet> tweetTree = tweetingService.retweetTweet(param.getTweetId());
                    Tree<Tweet> topTree = tweetTree.getParent();
                    while (topTree.getParent() != null) {
                        topTree = topTree.getParent();
                    }
                    Result result = new TweetResult(topTree);
                    Response response = new Response(false, 0, result);
                    sendResponse(response);
                    //TODO: LOG
                } else if (req.getMethod().equals("UnretweetTweet")) {
                    LikeTweetParameter param = (LikeTweetParameter) req.getParameterValues();
                    Tree<Tweet> tweetTree = tweetingService.unretweetTweet(param.getTweetId());
                    Tree<Tweet> topTree = tweetTree.getParent();
                    while (topTree.getParent() != null) {
                        topTree = topTree.getParent();
                    }
                    Result result = new TweetResult(topTree);
                    Response response = new Response(false, 0, result);
                    sendResponse(response);
                    //TODO: LOG
                } else if (req.getMethod().equals("GetProfile")) {
                    GetProfileParameter param = (GetProfileParameter) req.getParameterValues();
                    ArrayList<Tree<Tweet>> tweets = ObserverServiceImpl.getInstance().getUserTweets(param.getUsername());
                    Result result = new GetProfileResult(AuthenticatorServiceImpl.getInstance().fromUsername(param.getUsername()),tweets);
                    Response response = new Response(false, 0, result);
                    sendResponse(response);
                    //TODO: LOG
                } else if (req.getMethod().equals("GetTimeline")) {
                    ArrayList<Tree<Tweet>> tweets = timelineService.getTimeline();
                    Result result = new GetTimelineResult(tweets);
                    Response response = new Response(false, 0, result);
                    sendResponse(response);
                    //TODO: LOG
                } else if (req.getMethod().equals("Follow")) {
                    GetProfileParameter param = (GetProfileParameter) req.getParameterValues();
                    User user1 = AuthenticatorServiceImpl.getInstance().fromUsername(param.getUsername());
                    User user2 = user;
                    ObserverServiceImpl.getInstance().follow(user1, user2);
                    Result result = new UserResult(user);
                    Response response = new Response(false, 0, result);
                    sendResponse(response);
                    //TODO: LOG
                } else if (req.getMethod().equals("Unfollow")) {
                    GetProfileParameter param = (GetProfileParameter) req.getParameterValues();
                    User user1 = AuthenticatorServiceImpl.getInstance().fromUsername(param.getUsername());
                    User user2 = user;
                    ObserverServiceImpl.getInstance().unfollow(user1, user2);
                    Result result = new UserResult(user);
                    Response response = new Response(false, 0, result);
                    sendResponse(response);
                    //TODO: LOG
                } else if (req.getMethod().equals("GetFolowers")) {
                    Result result = new UserlistResult(user.getFollowers());
                    Response response = new Response(false, 0, result);
                    sendResponse(response);
                    //TODO: LOG
                } else if (req.getMethod().equals("GetFollowings")) {
                    Result result = new UserlistResult(user.getFollowings());
                    Response response = new Response(false, 0, result);
                    sendResponse(response);
                    //TODO: LOG
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendResponse(Response response) throws IOException {
        String resString = MapperSingleton.getObjectMapper().writeValueAsString(response);
        out.write(resString.getBytes());
    }

}
