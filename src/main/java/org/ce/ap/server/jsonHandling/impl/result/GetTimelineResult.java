package main.java.org.ce.ap.server.jsonHandling.impl.result;

import main.java.org.ce.ap.server.entity.Tweet;
import main.java.org.ce.ap.server.jsonHandling.Result;
import main.java.org.ce.ap.server.util.Tree;

import java.util.ArrayList;

public class GetTimelineResult extends Result {
    private ArrayList<Tree<Tweet>> timeline;

    public GetTimelineResult(ArrayList<Tree<Tweet>> timeline) {
        this.timeline = timeline;
    }

    public ArrayList<Tree<Tweet>> getTimeline() {
        return timeline;
    }
}