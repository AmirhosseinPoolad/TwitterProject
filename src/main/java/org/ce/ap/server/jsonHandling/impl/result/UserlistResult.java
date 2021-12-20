package main.java.org.ce.ap.server.jsonHandling.impl.result;

import main.java.org.ce.ap.server.jsonHandling.Result;

import java.util.ArrayList;

public class UserlistResult extends Result {
    ArrayList<String> users;

    public UserlistResult(ArrayList<String> users) {
        this.users = users;
    }

    public ArrayList<String> getUsers() {
        return users;
    }
}
