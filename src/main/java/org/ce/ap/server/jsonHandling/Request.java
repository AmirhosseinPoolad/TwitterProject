package main.java.org.ce.ap.server.jsonHandling;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Request {
    @JsonProperty
    private String method;
    @JsonProperty
    private String description;
    @JsonProperty
    private Parameter parameterValues;

    public String getMethod() {
        return method;
    }

    public String getDescription() {
        return description;
    }

    public Parameter getParameterValues() {
        return parameterValues;
    }
}
