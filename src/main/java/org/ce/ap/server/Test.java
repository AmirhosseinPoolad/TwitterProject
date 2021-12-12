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
        Tweet tweet1 = new Tweet("hdxc", "Hello Again!");
        Tweet tweet2 = new Tweet("brrrr", "ayyy bro!");
        tweetGraph.addTweet(tweet2, tweet1);
        System.out.println();
    }
}