package main.java.org.ce.ap.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Tweet implements ByteSerializable{
    //username of poster
    private String poster;
    //list of users that liked the tweet
    private ArrayList<String> likedUsers;
    //list of users that retweeted the tweet
    private ArrayList<String> retweetedUsers;
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
        this.retweetedUsers = new ArrayList<String>();
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

    public String getPoster() {
        return poster;
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

    @Override
    public ByteSerializable readFromFile(BufferedReader in, String firstLine) {
        Tweet newTweet = new Tweet(null, null);
        try {
            newTweet.poster = firstLine;
            String likedString = in.readLine();
            newTweet.likedUsers = new ArrayList<String>(Arrays.asList(likedString.split(", ")));
            String retweetedString = in.readLine();
            newTweet.likedUsers = new ArrayList<String>(Arrays.asList(retweetedString.split(", ")));
            String contentString = in.readLine();
            newTweet.content = contentString;
            String dateString = in.readLine();
            newTweet.postTime = LocalDateTime.parse(dateString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newTweet;
    }
}
