package main.java.org.ce.ap.server.impl;

import main.java.org.ce.ap.server.entity.Tweet;
import main.java.org.ce.ap.server.entity.TweetGraph;
import main.java.org.ce.ap.server.entity.User;
import main.java.org.ce.ap.server.services.TimelineService;
import main.java.org.ce.ap.server.util.Tree;

import java.util.ArrayList;

public class TimelineServiceImpl implements TimelineService {
    //list of all top level tweets from followed users
    private ArrayList<Tree<Tweet>> timeline;
    //user that is using the service
    private User user;
    /**
     * data is only ever added to tweetGraph.tweetTree, so if we iterated to index i of tweetTree there's no need to do it again.
     * this index keeps track of the last searched index to avoid iterating redundant data.
     */
    private int searchedIndex = 0;

    /**
     * constructs a new TimelineService for user
     *
     * @param user user that's using the TimelineService
     */
    public TimelineServiceImpl(User user) {
        timeline = new ArrayList<Tree<Tweet>>();
        this.user = user;
    }

    /**
     * get timeline of user
     *
     * @return arraylist containing top level tweets of user's timeline
     */
    @Override
    public ArrayList<Tree<Tweet>> getTimeline() {

        for (int i = searchedIndex; i < TweetGraph.getInstance().getTweetTree().size(); i++) {
            if (user.isFollowing(TweetGraph.getInstance().getTweetTree().get(i).getData().getPoster())) {
                timeline.add(TweetGraph.getInstance().getTweetTree().get(i));
            }
            searchedIndex++;
        }
        return timeline;
    }
}
