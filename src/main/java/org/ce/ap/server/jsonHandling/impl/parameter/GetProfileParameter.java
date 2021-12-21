package main.java.org.ce.ap.server.jsonHandling.impl.parameter;

import main.java.org.ce.ap.server.jsonHandling.Parameter;

public class GetProfileParameter extends Parameter {
    private String username;

    public GetProfileParameter(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
