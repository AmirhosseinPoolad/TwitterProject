package main.java.org.ce.ap.server;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Locale;
import java.util.Objects;

public class User {
    private String username;
    private String passwordHash;
    private String firstName;
    private String lastName;
    private String biography;
    private LocalDate birthdayDate;
    private LocalDate signUpDate;

    /**
     * constructs a new User object
     *
     * @param username          username
     * @param plaintextPassword password in plaintext, gets hashed
     * @param biography         biography of user. cannot be bigger than 256 characters
     * @throws IllegalArgumentException if biography is bigger than 256 characters
     * @throws NoSuchAlgorithmException if SHA-256 algorithm is not available (when does this happen?)
     */
    public User(String username, String plaintextPassword, String firstName, String lastName, String biography, LocalDate birthdayDate) throws IllegalArgumentException, NoSuchAlgorithmException {
        this.username = username;
        this.passwordHash = ServerUtil.byteToString(ServerUtil.getSHA(plaintextPassword));
        if (biography.length() > 256)
            throw new IllegalArgumentException("Biography is longer than 256 characters");
        this.biography = biography;
        this.firstName = firstName;
        this.lastName = lastName;
        this.signUpDate = LocalDate.now(ZoneOffset.UTC);
        this.birthdayDate = birthdayDate;
    }

    /**
     * checks if password is correct by hashing it and comparing it to passwordHash
     *
     * @param password plaintext password to check
     * @return true if password is correct
     */
    public boolean isPasswordCorrect(String password) {
        try {
            return passwordHash.equals(ServerUtil.byteToString(ServerUtil.getSHA(password)));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void printInfo() {
        System.out.println("@" + username);
        System.out.println(firstName + " " + lastName);
        System.out.println(biography);
        System.out.println("Signed up at " + signUpDate + " | Birthday: " + birthdayDate);
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
