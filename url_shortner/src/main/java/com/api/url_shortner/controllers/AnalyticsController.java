package com.api.url_shortner.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.api.url_shortner.models.GeneralAnalyticsResponse;
import com.api.url_shortner.models.SpecificAnalyticsResponse;
import com.api.url_shortner.services.AnalyticsService;
import com.google.gson.Gson;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/analytics")
@AllArgsConstructor
public class AnalyticsController {

     private final AnalyticsService analyticsService;

    @GetMapping()
    public ResponseEntity<String> getGeneralAnalytics() {
        GeneralAnalyticsResponse responseObject = analyticsService.getGeneralAnalytics();
        String respnseJsonObject = new Gson().toJson(responseObject);
        return ResponseEntity.status(HttpStatus.OK).body(respnseJsonObject);
    }

    @GetMapping("/specific")
    public ResponseEntity<String> getSpecificAnalytics(@RequestParam(name = "url", required = false) String url, @RequestParam(name = "shortUrl", required = false) String shortUrl) {
        System.out.println(url);
        System.out.println(shortUrl);
        SpecificAnalyticsResponse responseObject;
        if(shortUrl == null && url == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("bad request");
        }else if(url != null){
            responseObject = analyticsService.getSpecificAnalytics(url, false);
        }else{// shortUrl is not null
            responseObject = analyticsService.getSpecificAnalytics(shortUrl, true);
        }
        if(responseObject == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not Found");
        }
        String respnseJsonObject = new Gson().toJson(responseObject);
        return ResponseEntity.status(HttpStatus.OK).body(respnseJsonObject);
    }

}
