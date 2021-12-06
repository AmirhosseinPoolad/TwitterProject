package main.java.org.ce.ap.server;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;

public class Test {
    public static void main(String[] args) {
        //TODO: addUser method that checks for duplicate usernames (implement with a hashmap/hashset. shouldn't be too hard)
        User user1 = null;
        try {
            user1 = new User("HDxC", "1234", "Amirhossein", "Poolad",
                    "Some Random Feller", LocalDate.of(2000, 11, 10));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        System.out.println(user1.isPasswordCorrect("123"));
        System.out.println(user1.isPasswordCorrect("1234"));
        user1.printInfo();
        //TODO: store tweets in a tree like structure to support replies.
        Tweet tweet = new Tweet("hdxc", "Hello World!");
        tweet.printInfo();
        tweet.addLike("hdxc");
        tweet.printInfo();
        tweet.addLike("hdxc");
        tweet.printInfo();
    }
}