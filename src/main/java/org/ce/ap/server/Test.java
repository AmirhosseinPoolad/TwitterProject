package main.java.org.ce.ap.server;

import main.java.org.ce.ap.server.impl.AuthenticatorServiceImpl;
import main.java.org.ce.ap.server.services.AuthenticatorService;

import java.time.LocalDate;

public class Test {
    public static void main(String[] args) {
        /*AuthenticatorService authService = new AuthenticatorServiceImpl();
        authService.signUp("HDxC", "1234", "Amirhossein", "Poolad",
                "Some Random Feller", LocalDate.of(2000, 11, 10));
        System.out.println(authService.signUp("HDxC", "1234", "Amirhossein", "Poolad",
                "Some Random Feller", LocalDate.of(2000, 11, 10)));
        System.out.println(authService.signUp("BRRRR", "12345", "Amirhossein", "Poolad",
                "Some Random Feller", LocalDate.of(2000, 11, 10)));
        System.out.println(authService.logIn("hdxc", "1234"));
        System.out.println(authService.logIn("hdxc", "124"));
        System.out.println(authService.logIn("brrrr", "12345"));*/

        TweetGraph tweetGraph = TweetGraph.getInstance();
        Tweet tweet = new Tweet("hdxc", "Hello World!");
        Tweet tweet2 = new Tweet("hdxc", "Hello Again!");
        Tweet tweet3 = new Tweet("brrrr", "Welcome");
        //tweetGraph.addTweet(tweet, null);
        //tweetGraph.addTweet(tweet2, null);
        //tweetGraph.addTweet(tweet3, tweetGraph.getTweet(tweet));

        //TODO: store tweets in a tree like structure to support replies.

        tweet.printInfo();
        tweet.addLike("hdxc");
        tweet.printInfo();
        tweet.addLike("brrrr");
        //tweetGraph.save();
        //tweet.printInfo();
        //tweet.removeLike("brrrr");
        tweetGraph.read();
        System.out.println();
    }
}