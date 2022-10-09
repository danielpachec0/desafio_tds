package com.api.url_shortner.models;

import lombok.Data;

@Data
public class SpecificAnalyticsResponse {
    private String creationDateTime;
    private String timeSinceCreated;
    private int numberOfAccesses;
    private int numberOfCreationRequests;

    public SpecificAnalyticsResponse(String creationDateTime, String timeSinceCreated, int numberOfAccesses, int numberOfCreationRequests) {
        this.creationDateTime = creationDateTime;
        this.timeSinceCreated = timeSinceCreated;
        this.numberOfAccesses = numberOfAccesses;
        this.numberOfCreationRequests = numberOfCreationRequests;
    }
}
