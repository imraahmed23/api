package org.example.notion;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Properties{
    @JsonProperty("Job Name")
    public JobName jobName;
    @JsonProperty("Status")
    public Status status;
    @JsonProperty("Job Link")
    public JobLink jobLink;
}