package main.java.org.ce.ap.server.jsonHandling.impl.result;

import main.java.org.ce.ap.server.entity.User;
import main.java.org.ce.ap.server.jsonHandling.Result;

public class UserResult extends Result {
    private User user;

    public UserResult(User user) {
        this.user = user;
    }

    public UserResult() {
    }

    public User getUser() {
        return user;
    }
}
