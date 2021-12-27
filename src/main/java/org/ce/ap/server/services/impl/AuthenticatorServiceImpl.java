package main.java.org.ce.ap.server.services.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import main.java.org.ce.ap.server.jsonHandling.MapperSingleton;
import main.java.org.ce.ap.server.services.AuthenticatorService;
import main.java.org.ce.ap.server.entity.User;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
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
        String userFile = PropertyServiceImpl.getInstance().getProperty("server.users.file") + "/users.txt";
        try (InputStream in = new FileInputStream(userFile)) {
            byte[] buffer = new byte[1 << 20]; //1 megabyte buffer
            String resString;
            int read = in.read(buffer);
            if (read != -1) {
                resString = new String(buffer, 0, read);
                ArrayList<User> users = MapperSingleton.getObjectMapper().readValue(resString, new TypeReference<ArrayList<User>>() {
                });
                for (User user : users) {
                    usersMap.put(user.getUsername().toLowerCase(), user);
                }
            }
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
            save();
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

    @Override
    public User fromUsername(String username) {
        return usersMap.get(username);
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
        String userFile = PropertyServiceImpl.getInstance().getProperty("server.users.file") + "/users.txt";
        try (OutputStream outputStream = new FileOutputStream(userFile);) {
            ArrayList<User> users = new ArrayList<>(usersMap.values());
            MapperSingleton.getObjectMapper().writeValue(outputStream, users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
