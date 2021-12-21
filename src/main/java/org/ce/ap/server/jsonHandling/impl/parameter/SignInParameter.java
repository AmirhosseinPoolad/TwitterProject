package main.java.org.ce.ap.server.jsonHandling.impl.parameter;

import main.java.org.ce.ap.server.jsonHandling.Parameter;

public class SignInParameter extends Parameter {
    private String username;
    private String password;

    public SignInParameter() {
    }

    public SignInParameter(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
