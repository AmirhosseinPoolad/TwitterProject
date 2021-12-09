package main.java.org.ce.ap.server;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Objects;

public class Tweet {
    //username of poster
    private String poster;
    //list of users that liked the tweet
    private ArrayList<String> likedUsers;
    //content of the tweet
    private String content;
    //date of post. in UTC.
    private LocalDateTime postTime;

    /**
     * makes a tweet from poster with content. Automatically sets postTime to current UTC time.
     *
     * @param poster  username of the poster
     * @param content content of the tweet
     */
    public Tweet(String poster, String content) {
        this.poster = poster;
        this.content = content;
        this.likedUsers = new ArrayList<String>();
        this.postTime = LocalDateTime.now(ZoneOffset.UTC);
    }

    /**
     * adds a like from username
     *
     * @param username username of likee
     */
    public void addLike(String username) {
        if (!likedUsers.contains(username))
            likedUsers.add(username);
    }

    /**
     * removes a like from username
     *
     * @param username username of dislikee
     */
    public void removeLike(String username) {
        likedUsers.remove(username);
    }

    /**
     * prints tweet info
     */
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
