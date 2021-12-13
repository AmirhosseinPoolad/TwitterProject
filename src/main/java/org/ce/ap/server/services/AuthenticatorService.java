package main.java.org.ce.ap.server.services;

import main.java.org.ce.ap.server.entity.User;

import java.time.LocalDate;

public interface AuthenticatorService {

    /**
     * signs up a new user (if it's not already signed up)
     *
     * @param username          username
     * @param plaintextPassword password
     * @param firstName         firstname
     * @param lastName          last name
     * @param biography         biography, maximum 256 words
     * @param birthdayDate
     * @return newly made user if success, null if username exists or error happened.
     */
    public abstract User signUp(String username, String plaintextPassword, String firstName, String lastName, String biography, LocalDate birthdayDate);

    /**
     * Logs in to username with password. returns 1 if username/password pair is correct.
     *
     * @param username account username
     * @param password account password (plaintext)
     * @return user if user/pass pair is correct, null if incorrect or does not exist.
     */
    public abstract User logIn(String username, String password);

}
