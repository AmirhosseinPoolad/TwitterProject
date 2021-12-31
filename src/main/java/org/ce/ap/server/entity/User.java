package main.java.org.ce.ap.server.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import main.java.org.ce.ap.server.util.ServerUtil;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

public class User {
    //username
    private String username;
    @JsonProperty
    //hashed password
    private String passwordHash;
    //first name
    private String firstName;
    //last name
    private String lastName;
    //biography
    private String biography;
    //birthday date
    private LocalDate birthdayDate;
    //sign up date
    private LocalDate signUpDate;
    //following users
    private HashSet<String> followings;
    //follower users
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

        if (username == null || username.equals(""))
            throw new IllegalArgumentException("Empty username");
        this.username = username;

        if (plaintextPassword == null || plaintextPassword.equals(""))
            throw new IllegalArgumentException("Empty password");
        this.passwordHash = ServerUtil.byteToString(ServerUtil.getSHA(plaintextPassword));

        if (biography == null || biography.equals(""))
            throw new IllegalArgumentException("Empty biography");
        if (biography.length() > 256)
            throw new IllegalArgumentException("Biography is longer than 256 characters");
        this.biography = biography;

        if (firstName == null || firstName.equals(""))
            throw new IllegalArgumentException("Empty first name");
        this.firstName = firstName;

        if (lastName == null || lastName.equals(""))
            throw new IllegalArgumentException("Empty last name");
        this.lastName = lastName;

        this.signUpDate = LocalDate.now(ZoneOffset.UTC);

        if (birthdayDate == null)
            throw new IllegalArgumentException("Empty birthday date");
        this.birthdayDate = birthdayDate;

        this.followings = new HashSet<>();
        this.followers = new HashSet<>();
    }

    /**
     * private constructor, used only when deserializing a user from file
     */
    @JsonCreator
    private User(@JsonProperty("username") String username, @JsonProperty("passwordHash") String passwordHash,
                 @JsonProperty("firstName") String firstName, @JsonProperty("lastName") String lastName,
                 @JsonProperty("biography") String biography, @JsonProperty("birthdayDate") LocalDate birthdayDate,
                 @JsonProperty("signUpDate") LocalDate signUpDate, @JsonProperty("followings") HashSet<String> followings,
                 @JsonProperty("followers") HashSet<String> followers) {
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

    public ArrayList<String> getFollowings() {
        return new ArrayList<String>(followings);
    }

    public ArrayList<String> getFollowers() {
        return new ArrayList<String>(followers);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBiography() {
        return biography;
    }

    public LocalDate getBirthdayDate() {
        return birthdayDate;
    }

    public LocalDate getSignUpDate() {
        return signUpDate;
    }

    /**
     * prints user info (for clientside rendering)
     */
    public void printInfo() {
        System.out.println("@" + username);
        System.out.println(firstName + " " + lastName);
        System.out.println(biography);
        System.out.println("Signed up at " + signUpDate + " | Birthday: " + birthdayDate);
        System.out.println(followers.size() + " Followers, " + followings.size() + " Followings");
    }

    /**
     * checks if this is following username
     * @param username username to check
     * @return true if following
     */
    public boolean isFollowing(String username) {
        return followings.contains(username);
    }

    /**
     * adds username to following list. do not use, meant for observer service only.
     * @param username username to be added
     */
    public void addFollowing(String username) {
        followings.add(username);
    }

    /**
     * removes username to following list. do not use, meant for observer service only.
     * @param username username to be added
     */
    public void removeFollowing(String username) {
        followings.remove(username);
    }

    /**
     * adds username to follower list. do not use, meant for observer service only.
     * @param username username to be added
     */
    public void addFollower(String username) {
        followers.add(username);
    }

    /**
     * removes username to follower list. do not use, meant for observer service only.
     * @param username username to be added
     */
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
}
