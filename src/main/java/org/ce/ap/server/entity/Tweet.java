package main.java.org.ce.ap.server.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Objects;

public class Tweet{
    //username of poster
    @JsonProperty
    private String poster;
    //list of users that liked the tweet
    @JsonProperty
    private ArrayList<String> likedUsers;
    //list of users that retweeted the tweet
    @JsonProperty
    private ArrayList<String> retweetedUsers;
    //content of the tweet
    @JsonProperty
    private String content;
    //date of post. in UTC.
    @JsonProperty
    private LocalDateTime postTime;
    //unique id of tweet
    @JsonProperty
    private int tweetId;

    /**
     * makes a tweet from poster with content. Automatically sets postTime to current UTC time.
     *
     * @param poster  username of the poster
     * @param content content of the tweet
     */
    public Tweet(String poster, String content, int tweetId) {
        this.poster = poster;
        this.content = content;
        this.tweetId = tweetId;
        this.likedUsers = new ArrayList<String>();
        this.retweetedUsers = new ArrayList<String>();
        this.postTime = LocalDateTime.now(ZoneOffset.UTC);
    }

    //used for json handling
    public Tweet() {

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
     * adds a retweet from username
     *
     * @param username username of retweeter
     */
    public void addRetweet(String username) {
        if (!retweetedUsers.contains(username))
            retweetedUsers.add(username);
    }

    /**
     * removes a retweet from username
     *
     * @param username username of unretweeter
     */
    public void removeRetweet(String username) {
        retweetedUsers.remove(username);
    }

    /**
     * prints tweet info
     */
    public void printInfo() {
        System.out.println("@" + poster + ", " + postTime);
        System.out.println(content);
        System.out.println(likedUsers.size() + " likes");
    }

    /**
     * poster getter function
     *
     * @return poster of tweet
     */
    public String getPoster() {
        return poster;
    }

    public int getTweetId() {
        return tweetId;
    }

    public ArrayList<String> getLikedUsers() {
        return likedUsers;
    }

    public ArrayList<String> getRetweetedUsers() {
        return retweetedUsers;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getPostTime() {
        return postTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tweet tweet = (Tweet) o;
        return poster.equals(tweet.poster) && content.equals(tweet.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(poster, content, postTime);
    }
}
