package main.java.org.ce.ap.server.jsonHandling.impl.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import main.java.org.ce.ap.server.entity.User;
import main.java.org.ce.ap.server.jsonHandling.Result;

public class UserResult extends Result {
    private User user;

    public UserResult(@JsonProperty("user") User user) {
        this.user = user;
    }

    public UserResult() {
    }

    public User getUser() {
        return user;
    }
}
