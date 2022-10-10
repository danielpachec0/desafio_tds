package com.api.url_shortner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.api.url_shortner.collections.UrlMap;
import com.api.url_shortner.models.GeneralAnalyticsResponse;
import com.api.url_shortner.models.SpecificAnalyticsResponse;
import com.api.url_shortner.repositories.UrlMapRepository;
import com.api.url_shortner.services.AnalyticsService;
import static org.mockito.Mockito.when;

public class AnalyticsServiceTests {

    private AutoCloseable closeable;

    @InjectMocks
    AnalyticsService analyticsService;

    @Mock
    UrlMapRepository urlMapRepository;

    @BeforeEach
    void setUp() throws Exception {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    public void test_getGeneralAnalytics() throws Exception{
        UrlMap mockMostAccessed = new UrlMap("teste1", "teste1", LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(1), 1, 1);
        UrlMap mockMostRequested = new UrlMap("teste2", "teste2", LocalDateTime.now().plusHours(2), LocalDateTime.now().plusHours(2), 2, 2);
        UrlMap mockOldestCreated = new UrlMap("teste3", "teste3", LocalDateTime.now().plusHours(3), LocalDateTime.now().plusHours(3), 3, 3);
        UrlMap mockLastUrlCreated = new UrlMap("teste4", "teste4", LocalDateTime.now().plusHours(4), LocalDateTime.now().plusHours(4), 4, 4);
        UrlMap mockLastAccessedUrl = new UrlMap("teste5", "teste5", LocalDateTime.now().plusHours(5), LocalDateTime.now().plusHours(5), 5, 5);
        UrlMap mockOldestAccessedUrl = new UrlMap("teste6", "teste6", LocalDateTime.now().plusHours(6), LocalDateTime.now().plusHours(6), 6, 6);
        long mockCount = 10;
        when(urlMapRepository.findTopByOrderByNumberOfAccessesDesc()).thenReturn(mockMostAccessed);
        when(urlMapRepository.findTopByOrderByNumberOfCreationsRequestsDesc()).thenReturn(mockMostRequested);
        when(urlMapRepository.findTopByOrderByCreationDateAsc()).thenReturn(mockOldestCreated);
        when(urlMapRepository.findTopByOrderByCreationDateDesc()).thenReturn(mockLastUrlCreated);
        when(urlMapRepository.findTopByOrderByLastAccessDateDesc()).thenReturn(mockLastAccessedUrl);
        when(urlMapRepository.findTopByOrderByLastAccessDateAsc()).thenReturn(mockOldestAccessedUrl);
        when(urlMapRepository.count()).thenReturn(mockCount);
        
        GeneralAnalyticsResponse result = analyticsService.getGeneralAnalytics();
        
        assertNotNull(result);
        assertEquals(mockCount, result.getNumberOfUrlMaps());
        assertEquals(mockMostRequested.getUrl(), result.getMostRequestedUrl());
        assertEquals(mockMostRequested.getNumberOfCreationsRequests(), result.getMostRequestedNumberOfRequests());
        assertEquals(mockMostAccessed.getUrl(), result.getMostAccessedUrl());
        assertEquals(mockMostAccessed.getNumberOfAccesses(), result.getMostAccessedNumberOfRequests());
        assertEquals(mockLastUrlCreated.getUrl(), result.getLastUrlCreated());
        assertEquals(mockLastUrlCreated.getCreationDate().toString(), result.getLastUrlCreatedDateTime());
        assertEquals(mockOldestCreated.getUrl(), result.getOldestUrlCreated());
        assertEquals(mockOldestCreated.getCreationDate().toString(), result.getOldestUrlCreatedDateTime());
        assertEquals(mockLastAccessedUrl.getUrl(), result.getLastAccessedUrl());
        assertEquals(mockLastAccessedUrl.getLastAccessDate().toString(), result.getLastAccessedUrlDateTime());
        assertEquals(mockOldestAccessedUrl.getUrl(), result.getOldestAccessedUrl());
        assertEquals(mockOldestAccessedUrl.getLastAccessDate().toString(), result.getOldestAccessedUrlDateTime());

        closeable.close();
    }

    @Test
    public void test_getSpecifiAnalytics_from_a_url_present_in_the_database(){
        UrlMap mockUrlMap = new UrlMap("teste", "teste", LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(1), 1, 1);
        when(urlMapRepository.findByUrl(anyString())).thenReturn(Optional.of(mockUrlMap));
        SpecificAnalyticsResponse response = analyticsService.getSpecificAnalytics(anyString(), false);

        assertNotNull(response);
        assertEquals(mockUrlMap.getCreationDate().toString(), response.getCreationDateTime());
        assertEquals(mockUrlMap.getNumberOfAccesses(), response.getNumberOfAccesses());
        assertEquals(mockUrlMap.getNumberOfCreationsRequests(), response.getNumberOfCreationRequests());
    }

    @Test
    public void test_getSpecifiAnalytics_from_a_url_not_present_in_the_database(){
        when(urlMapRepository.findByUrl(anyString())).thenReturn(Optional.empty());
        SpecificAnalyticsResponse response = analyticsService.getSpecificAnalytics(anyString(), false);

        assertNull(response);
    }

    @Test
    public void test_getSpecifiAnalytics_from_a_shortUrl_present_in_the_database(){
        UrlMap mockUrlMap = new UrlMap("teste", "teste", LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(1), 1, 1);
        when(urlMapRepository.findByShortUrl(anyString())).thenReturn(Optional.of(mockUrlMap));
        SpecificAnalyticsResponse response = analyticsService.getSpecificAnalytics(anyString(), true);

        assertNotNull(response);
        assertEquals(mockUrlMap.getCreationDate().toString(), response.getCreationDateTime());
        assertEquals(mockUrlMap.getNumberOfAccesses(), response.getNumberOfAccesses());
        assertEquals(mockUrlMap.getNumberOfCreationsRequests(), response.getNumberOfCreationRequests());
    }

    @Test
    public void test_getSpecifiAnalytics_from_a_shortUrl_not_present_in_the_database(){
        when(urlMapRepository.findByUrl(anyString())).thenReturn(Optional.empty());
        SpecificAnalyticsResponse response = analyticsService.getSpecificAnalytics(anyString(), true);

        assertNull(response);

    }
}
