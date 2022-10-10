package com.api.url_shortner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileReader;
import java.util.Optional;
import java.util.Properties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.api.url_shortner.collections.UrlMap;
import com.api.url_shortner.repositories.UrlMapRepository;
import com.api.url_shortner.services.UrlMapService;



public class UrlMapServiceTests {

    private AutoCloseable closeable;

    @InjectMocks
    UrlMapService urlService;

    @Mock
    UrlMapRepository urlRepository;

    @BeforeEach
    void setUp() throws Exception {
        closeable = MockitoAnnotations.openMocks(this);
    }

    //testing checkFromUrl url when repository do find an isntance
    @Test
    void test_checkFromUrl_when_database_return_true() throws Exception {
        when(urlRepository.existsByUrl(anyString())).thenReturn(true);
        boolean result = urlService.checkFromUrl(anyString());
        assertNotNull(result);
        assertEquals(true, result);
        closeable.close();
    }

    //testing checkFromUrl url when repository do not find an isntance
    @Test
    void test_checkFromUrl_when_database_return_false() throws Exception {
        when(urlRepository.existsByUrl(anyString())).thenReturn(false);
        boolean result = urlService.checkFromUrl(anyString());
        assertNotNull(result);
        assertEquals(false, result);
        closeable.close();
    }

    //testing checkShortFromUrl url when repository do find an isntance
    @Test
    void test_checkFromShortUrl_database_return_true() throws Exception {
        when(urlRepository.existsByShortUrl(anyString())).thenReturn(true);
        boolean result = urlService.checkFromShortUrl(anyString());
        assertNotNull(result);
        assertEquals(true, result);
        closeable.close();
    }

    //testing checkFromShortUrl when repository do not find an isntance
    @Test
    void test_checkFromShortUrl_database_return_false() throws Exception {
        when(urlRepository.existsByShortUrl(anyString())).thenReturn(false);
        boolean result = urlService.checkFromShortUrl(anyString());
        assertNotNull(result);
        assertEquals(false, result);
        closeable.close();
    }

    @Test
    void test_validateUrl_when_valid_url_whith_https() throws Exception {
        String validUrl = "https://www.google.com";
        boolean result = urlService.validateUrl(validUrl);
        assertNotNull(result);
        assertEquals(true, result);
        closeable.close();
    }

    @Test
    void test_validateUrl_when_valid_url_whith_http() throws Exception {
        String validUrl = "http://www.google.com";
        boolean result = urlService.validateUrl(validUrl);
        assertNotNull(result);
        assertEquals(true, result);
        closeable.close();
    }

    @Test
    void test_validateUrl_when_valid_url_whithou_http() throws Exception {
        String validUrl = "www.google.com";
        boolean result = urlService.validateUrl(validUrl);
        assertNotNull(result);
        assertEquals(true, result);
        closeable.close();
    }

    
    @Test
    void test_validateUrl_when_invalid_url_whithou_http() throws Exception {
        String unvalidUrl = "not an url";
        boolean result = urlService.validateUrl(unvalidUrl);
        assertNotNull(result);
        assertEquals(false, result);
        closeable.close();
    }

    @Test
    void test_validateUrl_when_invalid_url_whith_http() throws Exception {
        String unvalidUrl = "http://not an url";
        boolean result = urlService.validateUrl(unvalidUrl);
        assertNotNull(result);
        assertEquals(false, result);
        closeable.close();
    }

    @Test
    void test_validateUrl_when_invalid_url_whith_https() throws Exception {
        String unvalidUrl = "https://not an url";
        boolean result = urlService.validateUrl(unvalidUrl);
        assertNotNull(result);
        assertEquals(false, result);
        closeable.close();
    }

    @Test
    void test_getFromUrl_when_databse_finds_the_instance() throws Exception {
        String inputUrl = "inputUrl";
        UrlMap urlObject = new UrlMap("inputUrl", "testeShort", null, null, 0, 0);
        when(urlRepository.findByUrl(anyString())).thenReturn(Optional.of(urlObject));
        UrlMap result = urlService.getFromUrl(anyString());
        assertNotNull(result);
        assertEquals("testeShort", result.getShortUrl());
        assertEquals(inputUrl, result.getUrl());
        closeable.close();
    }

    @Test
    void test_getFromUrl_when_not_find_instance() throws Exception {
        when(urlRepository.findByUrl(anyString())).thenReturn(Optional.empty());
        UrlMap result = urlService.getFromUrl(anyString());
        assertNull(result);
        closeable.close();
    }

    @Test
    void test_getFromShortUrl_when_databse_finds_the_instance() throws Exception {
        String inputShort = "inputShort";
        UrlMap urlObject = new UrlMap("originalUrl", "inputShort", null, null, 0, 0);
        when(urlRepository.findByShortUrl(inputShort)).thenReturn(Optional.of(urlObject));
        UrlMap result = urlService.getFromShortUrl(inputShort);
        assertNotNull(result);
        assertEquals("originalUrl", result.getUrl());
        assertEquals(inputShort, result.getShortUrl());
        closeable.close();
    }

    @Test
    void test_getFromShortUrl_when_not_find_instance() throws Exception {
        String inputShort = "inputShort";
        // Url urlObject = new Url("originalUrl", "inputShort", null, null, 0, 0);
        when(urlRepository.findByShortUrl(inputShort)).thenReturn(Optional.empty());
        UrlMap result = urlService.getFromShortUrl(inputShort);
        assertNull(result);
        closeable.close();
    }

    
    @Test
    void test_saveUrl() throws Exception {
        UrlMap urlObject = new UrlMap("originalUrl", "inputShort", null, null, 0, 0);
        urlObject.setId("idString");
        when(urlRepository.save(any())).thenReturn(urlObject);
        String result = urlService.saveUrl(urlObject);
        assertNotNull(result);
        assertEquals("idString", result);
        closeable.close();
    }

    //testing createNewUrl function and asserting that the counter is increasing when generating a new shortUrl
    @Test
    void test_createNewUrl() throws Exception {
        File configFile = new File("/home/daniel/Projects/desafio_tds/url_shortner/src/main/resources/config.properties");
		FileReader reader = new FileReader(configFile);
		Properties props = new Properties();
		props.load(reader);
        String originalCounteString = props.getProperty("counter");
        int originalCounter = Integer.parseInt(originalCounteString);
        reader.close();

        UrlMap urlObject = new UrlMap("originalUrl", "inputShort", null, null, 0, 0);
        when(urlRepository.save(any())).thenReturn(urlObject);
        String result = urlService.createNewUrl(anyString());
        
        reader = new FileReader(configFile);
        props.load(reader);
        String newCounterString = props.getProperty("counter");
        int newCounter = Integer.parseInt(newCounterString);
        reader.close();

        assertNotNull(result);
        assertEquals(newCounter, originalCounter+1);
        assertEquals(urlObject.getShortUrl(), result);
        closeable.close();
    }
}
