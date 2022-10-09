package com.api.url_shortner.controllers;

import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/analytics")
@AllArgsConstructor
public class AnalyticsController {

    // private final UrlMapService urlService;

    @GetMapping()
    public ResponseEntity<String> getGeneralAnalytics() {
        return ResponseEntity.status(HttpStatus.OK).body("requested general analytics");
    }

    @GetMapping("/{url_or_shortUrl}")
    public ResponseEntity<String> getSpecificAnalytics(@PathVariable(value = "url_or_shortUrl") String urlOrShortUrl) {
        return ResponseEntity.status(HttpStatus.OK).body("requested analitycs for = " + urlOrShortUrl);
    }

}
