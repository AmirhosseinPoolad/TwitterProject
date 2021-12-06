package main.java.org.ce.ap.server.impl;

import main.java.org.ce.ap.server.AuthenticatorService;
import main.java.org.ce.ap.server.User;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;

public class AuthenticatorServiceImpl extends AuthenticatorService {

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
}
