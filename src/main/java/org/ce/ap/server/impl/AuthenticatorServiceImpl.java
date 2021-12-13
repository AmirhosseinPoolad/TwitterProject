package main.java.org.ce.ap.server.impl;

import main.java.org.ce.ap.server.services.AuthenticatorService;
import main.java.org.ce.ap.server.entity.User;

import java.io.*;
import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AuthenticatorServiceImpl implements AuthenticatorService {
    /**
     * A map of users. Key is lowercase username and value is the User object.
     * Users should be unique in the map.
     */
    protected ConcurrentHashMap<String, User> usersMap;

    private AuthenticatorServiceImpl() {
        this.usersMap = new ConcurrentHashMap<String, User>();
        read();
    }

    private static AuthenticatorServiceImpl INSTANCE = null;

    public static synchronized AuthenticatorServiceImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AuthenticatorServiceImpl();
        }
        return INSTANCE;
    }

    /**
     * reads user data from files/model/users/users.txt and constructs usersMap from it
     */
    private void read() {
        BufferedReader inputStream = null;
        try (BufferedReader in = new BufferedReader(new FileReader("files/model/users/users.txt"))) {
            String firstLine;
            do {
                firstLine = in.readLine();
                if (firstLine == null)
                    break;
                User newUser = User.readFromFile(in, firstLine);
                usersMap.put(newUser.getUsername().toLowerCase(), newUser);
            } while (firstLine != null);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
     * @return newly made user if success, null if username exists or error happened.
     */
    @Override
    public User signUp(String username, String plaintextPassword, String firstName,
                       String lastName, String biography, LocalDate birthdayDate) {
        //only make a new user if it doesn't already exist
        if (!usersMap.containsKey(username.toLowerCase())) {
            User user = null;
            try {
                user = new User(username, plaintextPassword, firstName, lastName, biography, birthdayDate);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                return null;
            }
            usersMap.put(username.toLowerCase(), user);
            try (BufferedWriter outputStream = new BufferedWriter(new FileWriter("files/model/users/users.txt", true));) {
                user.writeToFile(outputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return user;
        }
        return null;
    }

    /**
     * Logs in to username with password. returns 1 if username/password pair is correct.
     *
     * @param username account username
     * @param password account password (plaintext)
     * @return user if user/pass pair is correct, null if incorrect or does not exist.
     */
    @Override
    public User logIn(String username, String password) {
        User user = usersMap.get(username.toLowerCase());
        //if user does not exist, return null
        if (user == null)
            return null;
        //else check if password is correct
        if (user.isPasswordCorrect(password)) {
            return user;
        } else {
            return null;
        }
    }

    /**
     * checks if user exists in usersMap
     *
     * @param username username to be checked
     * @return true if user exists
     */
    public boolean userExists(String username) {
        return usersMap.containsKey(username.toLowerCase());
    }

    /**
     * serializes and saves user data from usersMap to "files/model/users/users.txt"
     */
    public synchronized void save() {
        try (BufferedWriter outputStream = new BufferedWriter(new FileWriter("files/model/users/users.txt"));) {
            for (Map.Entry<String, User> entry : usersMap.entrySet()) {
                entry.getValue().writeToFile(outputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
