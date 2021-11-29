package main.java.org.ce.ap.server;

import java.security.NoSuchAlgorithmException;

public class User {
    private String username;
    private String passwordHash;
    private String biography;

    public User(String username, String plaintextPassword, String biography){
        this.username = username;
        this.biography = biography;
        try {
            this.passwordHash = ServerUtil.byteToString(ServerUtil.getSHA(plaintextPassword));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
