package main.java.org.ce.ap.server.jsonHandling.impl.parameter;

import com.fasterxml.jackson.annotation.JsonProperty;
import main.java.org.ce.ap.server.jsonHandling.Parameter;

public class SendTweetParameter extends Parameter {
    String content;
    int parentId;

    public String getContent() {
        return content;
    }

    public int getParentId() {
        return parentId;
    }
}
