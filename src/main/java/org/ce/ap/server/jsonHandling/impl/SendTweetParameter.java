package main.java.org.ce.ap.server.jsonHandling.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import main.java.org.ce.ap.server.jsonHandling.Parameter;

public class SendTweetParameter extends Parameter {
    @JsonProperty
    String content;
}
