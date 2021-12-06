package main.java.org.ce.ap.server;

import main.java.org.ce.ap.server.impl.AuthenticatorServiceImpl;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;

public class Test {
    public static void main(String[] args) {
        AuthenticatorService authService = new AuthenticatorServiceImpl();
        authService.signUp("HDxC", "1234", "Amirhossein", "Poolad",
                "Some Random Feller", LocalDate.of(2000, 11, 10));
        System.out.println(authService.signUp("HDxC", "1234", "Amirhossein", "Poolad",
                "Some Random Feller", LocalDate.of(2000, 11, 10)));
        System.out.println(authService.signUp("BRRRR", "12345", "Amirhossein", "Poolad",
                "Some Random Feller", LocalDate.of(2000, 11, 10)));
        System.out.println(authService.logIn("hdxc", "1234"));
        System.out.println(authService.logIn("hdxc", "124"));
        System.out.println(authService.logIn("brrrr", "12345"));


        //TODO: store tweets in a tree like structure to support replies.
        Tweet tweet = new Tweet("hdxc", "Hello World!");
        tweet.printInfo();
        tweet.addLike("hdxc");
        tweet.printInfo();
        tweet.addLike("brrrr");
        tweet.printInfo();
    }
}