package com.api.url_shortner.services;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.url_shortner.collections.UrlMap;
import com.api.url_shortner.models.GeneralAnalyticsResponse;
import com.api.url_shortner.models.SpecificAnalyticsResponse;
import com.api.url_shortner.repositories.UrlMapRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AnalyticsService {
    @Autowired
    private final UrlMapRepository urlMapRepository;

    public GeneralAnalyticsResponse getGeneralAnalytics() {
        GeneralAnalyticsResponse response;
        UrlMap mostAccessed = urlMapRepository.findTopByOrderByNumberOfAccessesDesc();
        UrlMap mostRequested = urlMapRepository.findTopByOrderByNumberOfCreationsRequestsDesc();
        UrlMap oldestCreated = urlMapRepository.findTopByOrderByCreationDateAsc();
        UrlMap lastUrlCreated = urlMapRepository.findTopByOrderByCreationDateDesc();
        UrlMap lastAccessedUrl = urlMapRepository.findTopByOrderByLastAccessDateDesc();
        UrlMap oldestAccessedUrl = urlMapRepository.findTopByOrderByLastAccessDateAsc();
        long numberOfUrlMaps = urlMapRepository.count();
        response = new GeneralAnalyticsResponse(numberOfUrlMaps, 
                mostRequested.getUrl(),
                mostRequested.getNumberOfCreationsRequests(),
                mostAccessed.getUrl(),
                mostAccessed.getNumberOfAccesses(),
                lastUrlCreated.getUrl(), 
                lastUrlCreated.getCreationDate().toString(), 
                oldestCreated.getUrl(),
                oldestCreated.getCreationDate().toString(),
                lastAccessedUrl.getUrl(),
                lastAccessedUrl.getLastAccessDate().toString(),
                oldestAccessedUrl.getUrl(),
                oldestAccessedUrl.getLastAccessDate().toString());

        return response;
    }

    public SpecificAnalyticsResponse getSpecificAnalytics(String input, boolean byShort) {
        UrlMap urlMap;
        if (byShort) {
            urlMap = urlMapRepository.findByShortUrl(input).orElse(null);
        } else {
            urlMap = urlMapRepository.findByUrl(input).orElse(null);
        }
        if (urlMap == null) {
            return null;
        }
        SpecificAnalyticsResponse response;
        Duration duration = Duration.between(LocalDateTime.now(), urlMap.getCreationDate());
        System.out.println(duration);
        response = new SpecificAnalyticsResponse(urlMap.getCreationDate().toString(), duration.toString(),
                urlMap.getNumberOfAccesses(), urlMap.getNumberOfCreationsRequests());
        System.out.println(response);
        return response;
    }
}
