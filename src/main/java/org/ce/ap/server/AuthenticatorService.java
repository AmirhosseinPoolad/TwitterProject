package main.java.org.ce.ap.server;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AuthenticatorService {
    /**
     * A map of users. Key is lowercase username and value is the User object.
     * Users should be unique in the map.
     */
    protected ConcurrentHashMap<String, User> usersMap;

    /**
     * signs up a new user (if it's not already signed up)
     *
     * @param username          username
     * @param plaintextPassword password
     * @param firstName         firstname
     * @param lastName          last name
     * @param biography         biography, maximum 256 words
     * @param birthdayDate
     */
    public abstract void signUp(String username, String plaintextPassword, String firstName, String lastName, String biography, LocalDate birthdayDate);

    /**
     * Logs in to username with password. returns 1 if username/password pair is correct.
     *
     * @param username account username
     * @param password account password (plaintext)
     * @return 1 if user/pass pair is correct, 0 if incorrect, -1 if user does not exist.
     */
    public abstract int login(String username, String password);

}
