package main.java.org.ce.ap.server.jsonHandling.impl;

import main.java.org.ce.ap.server.jsonHandling.Parameter;

public class SignInParameter extends Parameter {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
