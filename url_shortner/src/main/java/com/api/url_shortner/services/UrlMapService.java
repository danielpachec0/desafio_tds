package com.api.url_shortner.services;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.Properties;
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
    private String generateShortUrl() throws Exception {
        File configFile = new File("./src/main/resources/config.properties");
		
        FileReader reader = new FileReader(configFile);
 
		Properties props = new Properties();
		props.load(reader);

		System.out.println(props);
		reader.close();


        char[] charArray = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        String newShortUrl = "";
        String counterString = props.getProperty("counter");
        int counter = Integer.parseInt(counterString);

        int aux = counter;
        while (aux > 0){
            newShortUrl = newShortUrl + (charArray[aux % 62]);
            aux = aux / 62;
        }

        counter++;
        props.setProperty("counter", Integer.toString(counter));

        FileWriter writer = new FileWriter(configFile);
        props.store(writer, "host settings");
        writer.close();

        return newShortUrl;
    }

    public boolean validateUrl(String url) {   
        if ((url.length() >= 8 && !url.substring(0, 8).equals("https://")) &&
            (url.length() >= 7 && !url.substring(0, 7).equals("http://"))) 
            {
            url = "http://" + url;
        }
        String regexString = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        Pattern p = Pattern.compile(regexString);
        Matcher m = p.matcher(url);
        boolean result = m.matches();
        return result;

    }

    public String createNewUrl(String url) throws Exception{
        String shortUrl = generateShortUrl();
        UrlMap urlObject = new UrlMap(url, shortUrl, LocalDateTime.now(), null, 0, 1);
        return urlRepository.save(urlObject).getShortUrl();
    }

    public boolean checkFromUrl(String url) {
        return urlRepository.existsByUrl(url);
    }

    public boolean checkFromShortUrl(String shortUrl) {
        return urlRepository.existsByShortUrl(shortUrl);
    }

    public UrlMap getFromShortUrl(String shortUrl) {
        return urlRepository.findByShortUrl(shortUrl).orElse(null);
    }

    public UrlMap getFromUrl(String url) {
        return urlRepository.findByUrl(url).orElse(null);
    }

    public String saveUrl(UrlMap url) {
        return urlRepository.save(url).getId();
    }
}
