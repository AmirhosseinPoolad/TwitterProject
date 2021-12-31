package main.java.org.ce.ap.server.jsonHandling.impl.parameter;

import main.java.org.ce.ap.server.jsonHandling.Parameter;

/**
 * request parameter containing a tweet id
 */
public class LikeTweetParameter extends Parameter {
    int tweetId;

    public LikeTweetParameter() {
    }

    public LikeTweetParameter(int tweetId) {
        this.tweetId = tweetId;
    }

    public int getTweetId() {
        return tweetId;
    }
}
