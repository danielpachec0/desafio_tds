package com.api.url_shortner.controllers;


import org.springframework.web.bind.annotation.RestController;

import com.api.url_shortner.collections.UrlMap;
import com.api.url_shortner.services.UrlMapService;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.time.LocalDateTime;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/")
@AllArgsConstructor
public class UrlMapController {

    private final UrlMapService urlService;


    @GetMapping("/{shortUrl}")
    public ResponseEntity<String> get_url(@PathVariable(value = "shortUrl") String shortUrl) {
        UrlMap urlObject = urlService.getFromShortUrl(shortUrl);
        if (urlObject == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        urlObject.setNumberOfAccesses(urlObject.getNumberOfAccesses() + 1);
        urlObject.setLastAccessDate(LocalDateTime.now());
        urlService.saveUrl(urlObject);
        if(urlObject.getUrl().substring(0, 7).equals("http://") || urlObject.getUrl().substring(0, 8).equals("https://") ){
            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(urlObject.getUrl())).build();
        }else{
            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("http://" + urlObject.getUrl())).build();
        }
    }

    @PostMapping
    public ResponseEntity<String> post_url(@RequestParam(name = "url", required = false) String url) {
        String shortUrl;
        if (!urlService.validateUrl(url)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("requested url is not valid");
        }
        UrlMap urlObject = urlService.getFromUrl(url);
        if(urlObject == null){
            try{
                shortUrl = urlService.createNewUrl(url);
            }catch(Exception e){
                System.out.println(e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("internal error when creating short url, try again later.");
            }
        }else{
            shortUrl = urlObject.getShortUrl();
            urlObject.setNumberOfCreationsRequests((urlObject.getNumberOfCreationsRequests()+1));
            urlService.saveUrl(urlObject);
        }
        return ResponseEntity.status(HttpStatus.OK).body("shot url = " + shortUrl);    
    }
}
