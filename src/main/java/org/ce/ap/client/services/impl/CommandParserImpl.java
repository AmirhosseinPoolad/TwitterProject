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
import main.java.org.ce.ap.server.jsonHandling.impl.result.GetProfileResult;
import main.java.org.ce.ap.server.jsonHandling.impl.result.GetTimelineResult;
import main.java.org.ce.ap.server.jsonHandling.impl.result.TweetResult;
import main.java.org.ce.ap.server.util.Tree;

import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

//TODO: Sanitize inputs
public class CommandParserImpl implements CommandParser {
    ConsoleViewService consoleViewService;
    ConnectionService connectionService;

    private MenuStatus menuStatus = MenuStatus.MAIN;
    private Scanner sc;
    private ArrayList<Tree<Tweet>> tweetTree;

    public CommandParserImpl(OutputStream out, InputStream in) {
        sc = new Scanner(System.in);
        connectionService = new ConnectionServiceImpl(out, in);
        //TODO: construct consoleviewerservice
    }

    public MenuStatus getMenuStatus() {
        return menuStatus;
    }

    @Override
    public int handleInput() throws IllegalArgumentException {
        switch (menuStatus) {
            case MAIN: {
                //TODO: Use console view
                System.out.println("Enter 1 to Login, 2 to Register");
                int input = sc.nextInt();
                sc.nextLine(); //so that we discard the newline character
                if (input != 1 && input != 2) {
                    throw new IllegalArgumentException("Invalid Input");
                }
                if (input == 2)
                    menuStatus = MenuStatus.REGISTER;
                else if (input == 1)
                    menuStatus = MenuStatus.LOGIN;
                break;
            }
            case REGISTER: {
                //TODO: Use console view
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
                //TODO: Render timeline using ConsoleViewService
                System.out.println("1 to send new tweet, 2 to like tweet, 3 to dislike tweet, 4 to retweet," +
                        " 5 to unretweet, 6 to view profile, 7 to log off");
                int input = sc.nextInt();
                sc.nextLine(); //to eat the newline in the input buffer
                switch (input) {
                    case 1:
                        menuStatus = MenuStatus.SEND_TWEET;
                        break;
                    case 2:
                        menuStatus = MenuStatus.LIKE;
                        break;
                    case 3:
                        menuStatus = MenuStatus.DISLIKE;
                        break;
                    case 4:
                        menuStatus = MenuStatus.RETWEET;
                        break;
                    case 5:
                        menuStatus = MenuStatus.UNRETWEET;
                        break;
                    case 6:
                        menuStatus = MenuStatus.VIEW_PROFILE;
                        break;
                    case 7:
                        menuStatus = MenuStatus.LOGOFF;
                        break;
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
                //TODO: ID doesn't exist error handling
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
                //TODO: ID doesn't exist error handling
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
                //TODO: Don't get all of the timeline again! use the server response to update timeline locally
                //getTimeline();
                menuStatus = MenuStatus.TIMELINE;
                break;
            }
            case DISLIKE: {
                System.out.println("Write ID of tweet you want to unlike: ");
                //TODO: ID doesn't exist error handling
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
                //TODO: Don't get all of the timeline again! use the server response to update timeline locally
                //getTimeline();
                menuStatus = MenuStatus.TIMELINE;
                break;
            }
            case RETWEET: {
                System.out.println("Write ID of tweet you want to retweet: ");
                //TODO: ID doesn't exist error handling
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
                //TODO: Don't get all of the timeline again! use the server response to update timeline locally
                //getTimeline();
                menuStatus = MenuStatus.TIMELINE;
                break;
            }
            case UNRETWEET: {
                System.out.println("Write ID of tweet you want to unretweet: ");
                //TODO: ID doesn't exist error handling
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
                //TODO: Don't get all of the timeline again! use the server response to update timeline locally
                //getTimeline();
                menuStatus = MenuStatus.TIMELINE;
                break;
            }
            case VIEW_PROFILE: {
                System.out.println("Enter username of user you want to see");
                String username = sc.nextLine();
                //TODO: handle null or user doesn't exist error
                Parameter param = new GetProfileParameter(username);
                Request req = new Request("GetProfile", "Gets profile of specified user", param);
                Response serverResponse = connectionService.sendToServer(req);
                if (serverResponse.getErrorCode() != 0) {
                    System.err.println("Error: " + serverResponse.getErrorCode());
                    menuStatus = MenuStatus.TIMELINE;
                    break;
                }
                GetProfileResult res = (GetProfileResult) serverResponse.getResults();
                //TODO: Use ConsoleViewService to render user profile
                System.out.println("Press enter to go back to timeline");
                sc.nextLine();
                menuStatus = MenuStatus.TIMELINE;
            }
            case LOGOFF: {
                return 1;
            }
            default:
                break;
        }
        return 0;
    }

    @Override
    public Request parseString(String input) {
        return null;
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
