package com.api.url_shortner.services;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.url_shortner.collections.UrlMap;
import com.api.url_shortner.repositories.UrlMapRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UrlMapService {
    
    @Autowired
    private final UrlMapRepository urlRepository;


    //to update
    private String generateShortUrl(String url) {
        String shortUrl = "";
        for (int i = 0; i < 7; i++) {
            shortUrl = shortUrl + ThreadLocalRandom.current().nextInt(1, 7);
        }
        return shortUrl;
    }

    //tested
    public boolean validateUrl(String url) {   
        if ((url.length() >= 8 && !url.substring(0, 8).equals("https://")) &&
            (url.length() >= 7 && !url.substring(0, 7).equals("http://"))) 
            {
            System.out.println("inside if");
            url = "http://" + url;
        }
        String regexString = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        Pattern p = Pattern.compile(regexString);
        Matcher m = p.matcher(url);
        boolean result = m.matches();
        return result;

    }

    public String createNewUrl(String url) {
        String shortUrl = generateShortUrl(url);
        UrlMap urlObject = new UrlMap(url, shortUrl, LocalDateTime.now(), null, 0, 1);
        return urlRepository.save(urlObject).getShortUrl();
    }

    //tested
    public boolean checkFromUrl(String url) {
        return urlRepository.existsByUrl(url);
    }

    //tested
    public boolean checkFromShortUrl(String shortUrl) {
        return urlRepository.existsByShortUrl(shortUrl);
    }

    //tested
    public UrlMap getFromShortUrl(String shortUrl) {
        return urlRepository.findByShortUrl(shortUrl).orElse(null);
    }

    //tested
    public UrlMap getFromUrl(String url) {
        return urlRepository.findByUrl(url).orElse(null);
    }

    //tested
    public String saveUrl(UrlMap url) {
        return urlRepository.save(url).getId();
    }
}
