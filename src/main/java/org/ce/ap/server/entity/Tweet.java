package main.java.org.ce.ap.server.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import main.java.org.ce.ap.server.services.ByteSerializable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Tweet implements ByteSerializable {
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

    private Tweet(String poster, ArrayList<String> likedUsers, ArrayList<String> retweetedUsers, String content, LocalDateTime postTime, int tweetId) {
        this.poster = poster;
        this.likedUsers = likedUsers;
        this.retweetedUsers = retweetedUsers;
        this.content = content;
        this.postTime = postTime;
        this.tweetId = tweetId;
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

    /**
     * serializes and writes tweet info to a BufferedWriter
     *
     * @param out output BufferedWriter
     */
    @Override
    public void writeToFile(BufferedWriter out) {
        try {
            out.write(poster);
            out.newLine();
            out.write(Arrays.toString(likedUsers.toArray()).replace("[", "").replace("]", ""));
            out.newLine();
            out.write(Arrays.toString(retweetedUsers.toArray()).replace("[", "").replace("]", ""));
            out.newLine();
            out.write(content);
            out.newLine();
            out.write(postTime.toString());
            out.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * parse tweet info from BufferedReader and
     *
     * @param in        input BufferedReader
     * @param firstLine read a line before calling this function to see if there's more in the file, then pass it to this function
     * @return Tweet parsed from file
     */
    @Override
    public ByteSerializable readFromFile(BufferedReader in, String firstLine) {
        Tweet newTweet = new Tweet(null, null, 0);
        try {
            String poster = firstLine;
            String likedString = in.readLine();
            ArrayList<String> likedUsers = new ArrayList<String>(Arrays.asList(likedString.split(", ")));
            String retweetedString = in.readLine();
            ArrayList<String> retweetedUsers = new ArrayList<String>(Arrays.asList(retweetedString.split(", ")));
            String content = in.readLine();
            String dateString = in.readLine();
            LocalDateTime postTime = LocalDateTime.parse(dateString);
            int tweetId = Integer.parseInt(in.readLine());
            return new Tweet(poster, likedUsers, retweetedUsers, content, postTime, tweetId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newTweet;
    }
}
