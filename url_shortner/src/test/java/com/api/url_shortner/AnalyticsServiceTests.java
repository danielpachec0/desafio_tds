package com.api.url_shortner;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.api.url_shortner.repositories.UrlMapRepository;
import com.api.url_shortner.services.AnalyticsService;

public class AnalyticsServiceTests {

    private AutoCloseable closeable;

    @InjectMocks
    AnalyticsService analyticsService;

    @Mock
    UrlMapRepository urlRepository;

    @BeforeEach
    void setUp() throws Exception {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    public void test_getGeneralAnalytics_from_a_url_present_in_the_database(){
        fail("not implemented");
    }

    @Test
    public void test_getSpecifiAnalytics_from_a_url_present_in_the_database(){
        fail("not implemented");
    }

    @Test
    public void test_getSpecifiAnalytics_from_a_url_not_present_in_the_database(){
        fail("not implemented");

    }

    @Test
    public void test_getSpecifiAnalytics_from_a_shortUrl_present_in_the_database(){
        fail("not implemented");

    }

    @Test
    public void test_getSpecifiAnalytics_from_a_shortUrl_not_present_in_the_database(){
        fail("not implemented");

    }
}
