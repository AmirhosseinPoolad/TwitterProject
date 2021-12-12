package main.java.org.ce.ap.server;

import main.java.org.ce.ap.server.impl.AuthenticatorServiceImpl;
import main.java.org.ce.ap.server.impl.ObserverServiceImpl;
import main.java.org.ce.ap.server.services.AuthenticatorService;
import main.java.org.ce.ap.server.util.Tree;

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
        Tweet t = new Tweet("brrrr", "hello from the other siiiiiiiiiiiiiiiide");
        TweetGraph tweetGraph = TweetGraph.getInstance();
        ObserverServiceImpl observerService = ObserverServiceImpl.getInstance();
        System.out.println();
    }
}