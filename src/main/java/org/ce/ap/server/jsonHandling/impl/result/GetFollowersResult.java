package main.java.org.ce.ap.server.jsonHandling.impl.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import main.java.org.ce.ap.server.entity.User;
import main.java.org.ce.ap.server.jsonHandling.Result;

import java.util.ArrayList;

public class GetFollowersResult extends Result {
    private ArrayList<User> followers;

    public GetFollowersResult(@JsonProperty("followers") ArrayList<User> followers) {
        this.followers = followers;
    }

    public ArrayList<User> getFollowers() {
        return followers;
    }
}
