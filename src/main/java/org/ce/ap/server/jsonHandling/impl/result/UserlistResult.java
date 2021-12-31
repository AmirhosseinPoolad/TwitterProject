package main.java.org.ce.ap.server.jsonHandling.impl.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import main.java.org.ce.ap.server.jsonHandling.Result;

import java.util.ArrayList;

/**
 * results containing a list of users
 */
public class UserlistResult extends Result {
    ArrayList<String> users;

    public UserlistResult(@JsonProperty("users") ArrayList<String> users) {
        this.users = users;
    }

    public ArrayList<String> getUsers() {
        return users;
    }
}
