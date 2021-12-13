package main.java.org.ce.ap.server.impl;

import main.java.org.ce.ap.server.Tweet;
import main.java.org.ce.ap.server.TweetGraph;
import main.java.org.ce.ap.server.User;
import main.java.org.ce.ap.server.services.TimelineService;
import main.java.org.ce.ap.server.util.Tree;

import java.util.ArrayList;

public class TimelineServiceImpl implements TimelineService {
    private ArrayList<Tree<Tweet>> timeline;
    private int searchedIndex = 0;

    public TimelineServiceImpl() {
        timeline = new ArrayList<Tree<Tweet>>();
    }

    @Override
    public ArrayList<Tree<Tweet>> getTimeline(User user) {

        for (int i = searchedIndex; i < TweetGraph.getInstance().getTweetTree().size(); i++) {
            if (user.isFollowing(TweetGraph.getInstance().getTweetTree().get(i).getData().getPoster())) {
                timeline.add(TweetGraph.getInstance().getTweetTree().get(i));
            }
            searchedIndex++;
        }
        return timeline;
    }
}
