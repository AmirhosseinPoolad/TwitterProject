package main.java.org.ce.ap.server.jsonHandling;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class Request {
    @JsonProperty
    private String method;
    @JsonProperty
    private String description;
    @JsonProperty
    private Parameter parameterValues;

    public Request(String method, String description, Parameter parameterValues) {
        this.method = method;
        this.description = description;
        this.parameterValues = parameterValues;
    }

    public Request() {

    }

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
