package com.api.url_shortner.collections;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "url")
public class UrlMap {
    @Id
    private String id;

    @Indexed(unique = true)
    private String url;

    @Indexed(unique = true)
    private String shortUrl;

    private LocalDateTime creationDate;

    private LocalDateTime lastAccessDate;

    private int numberOfAccesses;

    private int numberOfCreationsRequests;

    public UrlMap(String url,
            String shortUrl,
            LocalDateTime creationDate,
            LocalDateTime lastAccessDate,
            int numberOfAccesses,
            int numberOfCreationsRequests) {
        this.url = url;
        this.shortUrl = shortUrl;
        this.creationDate = creationDate;
        this.lastAccessDate = lastAccessDate;
        this.numberOfAccesses = numberOfAccesses;
        this.numberOfCreationsRequests = numberOfCreationsRequests;
    }
}