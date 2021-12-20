package main.java.org.ce.ap.server.jsonHandling.impl.result;

import main.java.org.ce.ap.server.entity.Tweet;
import main.java.org.ce.ap.server.jsonHandling.Result;
import main.java.org.ce.ap.server.util.Tree;

public class TweetResult extends Result {
    private Tree<Tweet> topLevelTree;

    public TweetResult(Tree<Tweet> topLevelTree) {
        this.topLevelTree = topLevelTree;
    }

    public Tree<Tweet> getTopLevelTree() {
        return topLevelTree;
    }
}
