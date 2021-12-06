package main.java.org.ce.ap.server.impl;

import main.java.org.ce.ap.server.AuthenticatorService;
import main.java.org.ce.ap.server.User;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;

public class AuthenticatorServiceImpl extends AuthenticatorService {

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
    @Override
    public void signUp(String username, String plaintextPassword, String firstName,
                       String lastName, String biography, LocalDate birthdayDate) {
        //only make a new user if it doesn't already exist
        if (!usersMap.containsKey(username.toLowerCase())) {
            try {
                User user = new User(username, plaintextPassword, firstName, lastName, biography, birthdayDate);
                usersMap.put(username.toLowerCase(), user);
            } catch (NoSuchAlgorithmException | IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Logs in to username with password. returns 1 if username/password pair is correct.
     *
     * @param username account username
     * @param password account password (plaintext)
     * @return 1 if user/pass pair is correct, 0 if incorrect, -1 if user does not exist.
     */
    @Override
    public int login(String username, String password) {
        User user = usersMap.get(username.toLowerCase());
        //if user does not exist, return -1
        if (user == null)
            return -1;
            //else check if password is correct
        else return user.isPasswordCorrect(password) ? 1 : 0;
    }
}
