package main.java.org.ce.ap.client.services.impl;

import main.java.org.ce.ap.client.MenuStatus;
import main.java.org.ce.ap.client.services.CommandParser;
import main.java.org.ce.ap.client.services.ConnectionService;
import main.java.org.ce.ap.client.services.ConsoleViewService;
import main.java.org.ce.ap.server.entity.Tweet;
import main.java.org.ce.ap.server.jsonHandling.Parameter;
import main.java.org.ce.ap.server.jsonHandling.Request;
import main.java.org.ce.ap.server.jsonHandling.Response;
import main.java.org.ce.ap.server.jsonHandling.impl.parameter.*;
import main.java.org.ce.ap.server.jsonHandling.impl.result.*;
import main.java.org.ce.ap.server.util.Tree;

import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class CommandParserImpl implements CommandParser {
    //console view service that handles rendering the timeline and profiles
    ConsoleViewService consoleViewService;
    //connection service that handles connecting and i/o with server
    ConnectionService connectionService;

    //status of menu state machine
    private MenuStatus menuStatus = MenuStatus.MAIN;
    //scanner for user input
    private Scanner sc;
    //current timeline, locally saved to do less i/o with server
    private ArrayList<Tree<Tweet>> tweetTree;

    public CommandParserImpl(OutputStream out, InputStream in) {
        sc = new Scanner(System.in);
        connectionService = new ConnectionServiceImpl(out, in);
        consoleViewService = new ConsoleViewServiceImpl();
    }

    /**
     * handles input and controls the client
     * @return 1 if we quit the program
     */
    @Override
    public int handleInput(){
        switch (menuStatus) {
            case MAIN: {
                System.out.println("Enter 1 to Login, 2 to Register");
                int input = sc.nextInt();
                sc.nextLine(); //so that we discard the newline character
                if (input != 1 && input != 2) {
                    System.err.println("Invalid input. Try again.");
                    break;
                }
                if (input == 2)
                    menuStatus = MenuStatus.REGISTER;
                else if (input == 1)
                    menuStatus = MenuStatus.LOGIN;
                break;
            }
            case REGISTER: {
                System.out.println("Enter your username, password, firstname, lastname, biography and birthday date in that order.");
                String username = sc.nextLine();
                String password = sc.nextLine();
                String firstName = sc.nextLine();
                String lastName = sc.nextLine();
                String biography = sc.nextLine();
                LocalDate birthdayDate = LocalDate.parse(sc.nextLine());
                RegisterParameter param = new RegisterParameter(username, password, firstName, lastName, biography, birthdayDate);
                Request req = new Request("Register", "Registers a new account and logs in", param);
                Response serverResponse = connectionService.sendToServer(req);
                if (serverResponse.getErrorCode() != 0) {
                    System.err.println("Error, please try again");
                    menuStatus = MenuStatus.MAIN;
                    return 0;
                }
                System.out.println("Succesfully registered.");
                getTimeline();
                menuStatus = MenuStatus.TIMELINE;
                break;
            }
            case LOGIN: {
                System.out.println("Enter your username and password.");
                String username = sc.nextLine();
                String password = sc.nextLine();
                SignInParameter param = new SignInParameter(username, password);
                Request req = new Request("SignIn", "Logins to account", param);
                Response serverResponse = connectionService.sendToServer(req);
                if (serverResponse.getErrorCode() != 0) {
                    System.err.println("Error, please try again");
                    menuStatus = MenuStatus.MAIN;
                    return 0;
                }
                System.out.println("Succesfully logged in.");
                getTimeline();
                menuStatus = MenuStatus.TIMELINE;
                break;
            }
            case TIMELINE: {
                System.out.print("\033[H\033[2J"); //clears the screen
                System.out.flush();
                consoleViewService.showTweetTree(tweetTree);
                System.out.println("1 to send new tweet, 2 to like tweet, 3 to dislike tweet, 4 to retweet," +
                        " 5 to unretweet, 6 to view profile, 7 to follow, 8 to unfollow, 9 to refresh, 10 to log off");
                int input = sc.nextInt();
                sc.nextLine(); //to eat the newline in the input buffer
                switch (input) {
                    case 1 : menuStatus = MenuStatus.SEND_TWEET;break;
                    case 2 : menuStatus = MenuStatus.LIKE;break;
                    case 3 : menuStatus = MenuStatus.DISLIKE;break;
                    case 4 : menuStatus = MenuStatus.RETWEET;break;
                    case 5 : menuStatus = MenuStatus.UNRETWEET;break;
                    case 6 : menuStatus = MenuStatus.VIEW_PROFILE;break;
                    case 7 : menuStatus = MenuStatus.FOLLOW;break;
                    case 8 : menuStatus = MenuStatus.UNFOLLOW;break;
                    case 9 : menuStatus = MenuStatus.REFRESH;break;
                    case 10 : menuStatus = MenuStatus.LOGOFF;break;
                    default : menuStatus = MenuStatus.TIMELINE;break;
                }
                break;

            }
            case SEND_TWEET: {
                System.out.println("Write your message and ID of tweet you're replying(If you're not replying press enter):");
                String tweetMsg = sc.nextLine();
                String IDString = sc.nextLine();
                int ID;
                try {
                    ID = Integer.parseInt(IDString);
                } catch (NumberFormatException e) {
                    ID = -1;
                }
                Parameter param = new SendTweetParameter(tweetMsg, ID);
                Request req = new Request("SendTweet", "Sends new tweet from logged in user", param);
                Response serverResponse = connectionService.sendToServer(req);
                if (serverResponse.getErrorCode() != 0) {
                    System.err.println("Error: " + serverResponse.getErrorCode());
                    menuStatus = MenuStatus.TIMELINE;
                    break;
                }
                getTimeline();
                menuStatus = MenuStatus.TIMELINE;
                break;
            }
            case LIKE: {
                System.out.println("Write ID of tweet you want to like: ");
                String IDString = sc.nextLine();
                int ID;
                try {
                    ID = Integer.parseInt(IDString);
                } catch (NumberFormatException e) {
                    ID = -1;
                }
                Parameter param = new LikeTweetParameter(ID);
                Request req = new Request("LikeTweet", "Likes tweet with given ID", param);
                Response serverResponse = connectionService.sendToServer(req);
                if (serverResponse.getErrorCode() != 0) {
                    System.err.println("Error: " + serverResponse.getErrorCode());
                    menuStatus = MenuStatus.TIMELINE;
                    break;
                }
                TweetResult res = (TweetResult) serverResponse.getResults();
                tweetTree.set(tweetTree.indexOf(res.getTopLevelTree()), res.getTopLevelTree());
                menuStatus = MenuStatus.TIMELINE;
                break;
            }
            case DISLIKE: {
                System.out.println("Write ID of tweet you want to unlike: ");
                String IDString = sc.nextLine();
                int ID;
                try {
                    ID = Integer.parseInt(IDString);
                } catch (NumberFormatException e) {
                    ID = -1;
                }
                Parameter param = new LikeTweetParameter(ID);
                Request req = new Request("DisikeTweet", "Unlikes tweet with given ID", param);
                Response serverResponse = connectionService.sendToServer(req);
                if (serverResponse.getErrorCode() != 0) {
                    System.err.println("Error: " + serverResponse.getErrorCode());
                    menuStatus = MenuStatus.TIMELINE;
                    break;
                }
                TweetResult res = (TweetResult) serverResponse.getResults();
                tweetTree.set(tweetTree.indexOf(res.getTopLevelTree()), res.getTopLevelTree());
                menuStatus = MenuStatus.TIMELINE;
                break;
            }
            case RETWEET: {
                System.out.println("Write ID of tweet you want to retweet: ");
                String IDString = sc.nextLine();
                int ID;
                try {
                    ID = Integer.parseInt(IDString);
                } catch (NumberFormatException e) {
                    ID = -1;
                }
                Parameter param = new LikeTweetParameter(ID);
                Request req = new Request("RetweetTweet", "Retweets tweet with given ID", param);
                Response serverResponse = connectionService.sendToServer(req);
                if (serverResponse.getErrorCode() != 0) {
                    System.err.println("Error: " + serverResponse.getErrorCode());
                    menuStatus = MenuStatus.TIMELINE;
                    break;
                }
                TweetResult res = (TweetResult) serverResponse.getResults();
                tweetTree.set(tweetTree.indexOf(res.getTopLevelTree()), res.getTopLevelTree());
                //getTimeline();
                menuStatus = MenuStatus.TIMELINE;
                break;
            }
            case UNRETWEET: {
                System.out.println("Write ID of tweet you want to unretweet: ");
                String IDString = sc.nextLine();
                int ID;
                try {
                    ID = Integer.parseInt(IDString);
                } catch (NumberFormatException e) {
                    ID = -1;
                }
                Parameter param = new LikeTweetParameter(ID);
                Request req = new Request("UnretweetTweet", "Retweets tweet with given ID", param);
                Response serverResponse = connectionService.sendToServer(req);
                if (serverResponse.getErrorCode() != 0) {
                    System.err.println("Error: " + serverResponse.getErrorCode());
                    menuStatus = MenuStatus.TIMELINE;
                    break;
                }
                TweetResult res = (TweetResult) serverResponse.getResults();
                tweetTree.set(tweetTree.indexOf(res.getTopLevelTree()), res.getTopLevelTree());
                menuStatus = MenuStatus.TIMELINE;
                break;
            }
            case VIEW_PROFILE: {
                System.out.println("Enter username of user you want to see");
                String username = sc.nextLine();
                Parameter param = new GetProfileParameter(username);
                Request req = new Request("GetProfile", "Gets profile of specified user", param);
                Response serverResponse = connectionService.sendToServer(req);
                if (serverResponse.getErrorCode() != 0) {
                    System.err.println("Error: " + serverResponse.getErrorCode());
                    menuStatus = MenuStatus.TIMELINE;
                    break;
                }
                GetProfileResult res = (GetProfileResult) serverResponse.getResults();
                consoleViewService.showUserInfo(res.getUser());
                consoleViewService.showTweetTree(res.getTweets());
                System.out.println("Press enter to go back to timeline");
                sc.nextLine();
                menuStatus = MenuStatus.TIMELINE;
                break;
            }
            case LIST_FOLLOWERS: {
                Request req = new Request("GetFollowers", "Gets followers of specified user", null);
                Response serverResponse = connectionService.sendToServer(req);
                if (serverResponse.getErrorCode() != 0) {
                    System.err.println("Error, please try again");
                    menuStatus = MenuStatus.TIMELINE;
                    break;
                }
                UserlistResult res = (UserlistResult) serverResponse.getResults();
                consoleViewService.showUserList(res.getUsers());
                System.out.println("Press enter to go back to timeline");
                sc.nextLine();
                menuStatus = MenuStatus.TIMELINE;
                break;
            }
            case LIST_FOLLOWINGS: {
                Request req = new Request("GetFollowings", "Gets followings of specified user", null);
                Response serverResponse = connectionService.sendToServer(req);
                if (serverResponse.getErrorCode() != 0) {
                    System.err.println("Error, please try again");
                    menuStatus = MenuStatus.TIMELINE;
                    break;
                }
                UserlistResult res = (UserlistResult) serverResponse.getResults();
                consoleViewService.showUserList(res.getUsers());
                System.out.println("Press enter to go back to timeline");
                sc.nextLine();
                menuStatus = MenuStatus.TIMELINE;
                break;
            }
            case FOLLOW: {
                System.out.println("Enter username to follow");
                String username = sc.nextLine();
                Parameter param = new GetProfileParameter(username);
                Request req = new Request("Follow", "Follows specified user", param);
                Response serverResponse = connectionService.sendToServer(req);
                if (serverResponse.getErrorCode() != 0) {
                    System.out.println("Error: Could not follow user");
                }
                getTimeline();
                menuStatus = MenuStatus.TIMELINE;
                break;
            }
            case UNFOLLOW: {
                System.out.println("Enter username to unfollow");
                String username = sc.nextLine();
                Parameter param = new GetProfileParameter(username);
                Request req = new Request("Unfollow", "Unfollows specified user", param);
                Response serverResponse = connectionService.sendToServer(req);
                if (serverResponse.getErrorCode() != 0) {
                    System.out.println("Error: Could not unfollow user");
                }
                getTimeline();
                menuStatus = MenuStatus.TIMELINE;
                break;
            }
            case REFRESH: {
                getTimeline();
                menuStatus = MenuStatus.TIMELINE;
                break;
            }
            case LOGOFF: {
                return 1;
            }
            default:
                break;
        }
        return 0;
    }

    /**
     * requests and gets timeline from server
     */
    private void getTimeline() {
        Request req = new Request("GetTimeline", "Get all of the timeline from the server", null);
        Response serverResponse = connectionService.sendToServer(req);
        if (serverResponse.getErrorCode() != 0) {
            System.err.println("Error, please try again");
            menuStatus = MenuStatus.MAIN;
            return;
        }
        GetTimelineResult res = (GetTimelineResult) serverResponse.getResults();
        this.tweetTree = res.getTimeline();
    }
}
