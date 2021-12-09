package main.java.org.ce.ap.server;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Objects;

public class Tweet {
    private String poster;
    private ArrayList<String> likedUsers;
    private String content;
    private LocalDateTime postTime;

    public Tweet(String poster, String content) {
        this.poster = poster;
        this.content = content;
        this.likedUsers = new ArrayList<String>();
        this.postTime = LocalDateTime.now(ZoneOffset.UTC);
    }

    public void addLike(String username) {
        if (!likedUsers.contains(username))
            likedUsers.add(username);
    }

    public void removeLike(String username) {
        likedUsers.remove(username);
    }

    public void printInfo() {
        System.out.println("@" + poster + ", " + postTime);
        System.out.println(content);
        System.out.println(likedUsers.size() + " likes");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tweet tweet = (Tweet) o;
        return poster.equals(tweet.poster) && content.equals(tweet.content) && postTime.equals(tweet.postTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(poster, content, postTime);
    }
}
