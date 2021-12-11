package main.java.org.ce.ap.server.impl;

import main.java.org.ce.ap.server.services.AuthenticatorService;
import main.java.org.ce.ap.server.User;

import java.time.LocalDate;
import java.util.concurrent.ConcurrentHashMap;

public class AuthenticatorServiceImpl implements AuthenticatorService {
    /**
     * A map of users. Key is lowercase username and value is the User object.
     * Users should be unique in the map.
     */
    protected ConcurrentHashMap<String, User> usersMap;

    public AuthenticatorServiceImpl() {
        this.usersMap = new ConcurrentHashMap<String, User>();
    }

    /**
     * signs up a new user (if it's not already signed up)
     *
     * @param username          username
     * @param plaintextPassword password
     * @param firstName         firstname
     * @param lastName          last name
     * @param biography         biography, maximum 256 words
     * @param birthdayDate
     * @return 1 if success, 0 if username already exists, -1 if error.
     */
    @Override
    public int signUp(String username, String plaintextPassword, String firstName,
                      String lastName, String biography, LocalDate birthdayDate) {
        //only make a new user if it doesn't already exist
        if (!usersMap.containsKey(username.toLowerCase())) {
            User user = null;
            try {
                user = new User(username, plaintextPassword, firstName, lastName, biography, birthdayDate);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                return -1;
            }
            usersMap.put(username.toLowerCase(), user);
            return 1;
        }
        return 0;
    }

    /**
     * Logs in to username with password. returns 1 if username/password pair is correct.
     *
     * @param username account username
     * @param password account password (plaintext)
     * @return 1 if user/pass pair is correct, 0 if incorrect, -1 if user does not exist.
     */
    @Override
    public int logIn(String username, String password) {
        User user = usersMap.get(username.toLowerCase());
        //if user does not exist, return -1
        if (user == null)
            return -1;
            //else check if password is correct
        else return user.isPasswordCorrect(password) ? 1 : 0;
    }
}
