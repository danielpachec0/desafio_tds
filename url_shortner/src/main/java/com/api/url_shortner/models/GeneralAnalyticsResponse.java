package com.api.url_shortner.models;

import lombok.Data;

@Data
public class GeneralAnalyticsResponse {
    private int numberOfUrlMaps;
    private String mostRequestedUrl;
    private int mostRequestedNumberOfRequests;
    private String mostAccessedUrl;
    private int mostAccessedNumberOfRequests;
    private String lastUrlCreated;
    private String lastUrlCreatedDateTime;
    private String oldestUrlCreated;
    private String oldestUrlCreatedDateTime;
    private String lastAccessedUrl;
    private String lastAccessedUrlDateTime;

    

    public GeneralAnalyticsResponse(int numberOfUrlMaps, String mostRequestedUrl, int mostRequestedNumberOfRequests,
            String mostAccessedUrl, int mostAccessedNumberOfRequests, String lastUrlCreated,
            String lastUrlCreatedDateTime, String oldestUrlCreated, String oldestUrlCreatedDateTime, String lastAccessedUrl,
            String lastAccessedUrlDateTime) {
        this.numberOfUrlMaps = numberOfUrlMaps;
        this.mostRequestedUrl = mostRequestedUrl;
        this.mostRequestedNumberOfRequests = mostRequestedNumberOfRequests;
        this.mostAccessedUrl = mostAccessedUrl;
        this.mostAccessedNumberOfRequests = mostAccessedNumberOfRequests;
        this.lastUrlCreated = lastUrlCreated;
        this.lastUrlCreatedDateTime = lastUrlCreatedDateTime;
        this.oldestUrlCreated = oldestUrlCreated;
        this.oldestUrlCreatedDateTime = oldestUrlCreatedDateTime;
        this.lastAccessedUrl = lastAccessedUrl;
        this.lastAccessedUrlDateTime = lastAccessedUrlDateTime;
    }

    
}
