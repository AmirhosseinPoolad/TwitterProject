package main.java.org.ce.ap.server.entity;

import main.java.org.ce.ap.server.services.ByteSerializable;
import main.java.org.ce.ap.server.util.ServerUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;

public class User {
    private String username;
    private String passwordHash;
    private String firstName;
    private String lastName;
    private String biography;
    private LocalDate birthdayDate;
    private LocalDate signUpDate;
    private HashSet<String> followings;
    private HashSet<String> followers;

    /**
     * constructs a new User object
     *
     * @param username          username
     * @param plaintextPassword password in plaintext, gets hashed
     * @param biography         biography of user. cannot be bigger than 256 characters
     * @throws IllegalArgumentException if biography is bigger than 256 characters
     */
    public User(String username, String plaintextPassword, String firstName, String lastName, String biography, LocalDate birthdayDate) throws IllegalArgumentException {
        this.username = username;
        this.passwordHash = ServerUtil.byteToString(ServerUtil.getSHA(plaintextPassword));
        if (biography.length() > 256)
            throw new IllegalArgumentException("Biography is longer than 256 characters");
        this.biography = biography;
        this.firstName = firstName;
        this.lastName = lastName;
        this.signUpDate = LocalDate.now(ZoneOffset.UTC);
        this.birthdayDate = birthdayDate;
        this.followings = new HashSet<>();
        this.followers = new HashSet<>();
    }

    private User(String username, String passwordHash, String firstName, String lastName, String biography, LocalDate birthdayDate, LocalDate signUpDate, HashSet<String> followings, HashSet<String> followers) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.firstName = firstName;
        this.lastName = lastName;
        this.biography = biography;
        this.birthdayDate = birthdayDate;
        this.signUpDate = signUpDate;
        this.followings = followings;
        this.followers = followers;
    }

    /**
     * checks if password is correct by hashing it and comparing it to passwordHash
     *
     * @param password plaintext password to check
     * @return true if password is correct
     */
    public boolean isPasswordCorrect(String password) {
        return passwordHash.equals(ServerUtil.byteToString(ServerUtil.getSHA(password)));
    }

    public String getUsername() {
        return username;
    }

    public void printInfo() {
        System.out.println("@" + username);
        System.out.println(firstName + " " + lastName);
        System.out.println(biography);
        System.out.println("Signed up at " + signUpDate + " | Birthday: " + birthdayDate);
    }

    public boolean isFollowing(String username) {
        return followings.contains(username);
    }

    public void addFollowing(String username) {
        followings.add(username);
    }

    public void removeFollowing(String username) {
        followings.remove(username);
    }

    public void addFollower(String username) {
        followers.add(username);
    }

    public void removeFollower(String username) {
        followers.remove(username);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return username.equals(user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    public void writeToFile(BufferedWriter out) {
        try {
            out.write(username);
            out.newLine();
            out.write(passwordHash);
            out.newLine();
            out.write(firstName);
            out.newLine();
            out.write(lastName);
            out.newLine();
            out.write(biography);
            out.newLine();
            out.write(birthdayDate.toString());
            out.newLine();
            out.write(signUpDate.toString());
            out.newLine();

            out.write(Arrays.toString(followings.toArray()).replace("[", "").replace("]", ""));
            out.newLine();
            out.write(Arrays.toString(followers.toArray()).replace("[", "").replace("]", ""));
            out.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static User readFromFile(BufferedReader in, String firstLine) {
        User newUser = null;
        try {
            String username = firstLine;
            String passwordHash = in.readLine();
            String firstName = in.readLine();
            String lastName = in.readLine();
            String biography = in.readLine();
            LocalDate birthdayDate = LocalDate.parse(in.readLine());
            LocalDate signUpDate = LocalDate.parse(in.readLine());
            HashSet<String> followings = new HashSet<String>(Arrays.asList(in.readLine().split(", ")));
            HashSet<String> followers = new HashSet<String>(Arrays.asList(in.readLine().split(", ")));
            newUser = new User(username, passwordHash, firstName, lastName, biography, birthdayDate, signUpDate, followings, followers);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newUser;
    }
}
