package com.api.url_shortner;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class AnalyticsControllerTests {

    
    @Autowired
    MockMvc mockMvc;

    //test when request the general analytics route
    @Test
    public void test_getGeneralAnalytics() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/analytics")).andExpect(MockMvcResultMatchers.status().isOk());
    }

    //test when requesting the specific analytics route with an url that is alredy stored in the database
    @Test
    public void test_getSpecificAnalytics_ok_from_url() throws Exception  {
        String validUrl = "https://google.com";
        mockMvc.perform(MockMvcRequestBuilders
        .get("/analytics/specific").param("url", validUrl)).andExpect(MockMvcResultMatchers.status().isOk());
    }

    //test when requesting the specific analytics route with an shortUrl that is alredy stored in the database
    @Test
    public void test_getSpecificAnalytics_ok_from_shortUrl()  throws Exception  {
        String validShortUrl =  "3565636";
        mockMvc.perform(MockMvcRequestBuilders
        .get("/analytics/specific").param("shortUrl", validShortUrl)).andExpect(MockMvcResultMatchers.status().isOk());
    }

    //test when requesting the specific analytics route with an shortUrl and a url
    @Test
    public void test_getSpecificAnalytics_ok_from_url_and_shortUrl()  throws Exception  {
        String validUrl = "https://google.com";
        String validShortUrl =  "3565636";

        mockMvc.perform(MockMvcRequestBuilders
        .get("/analytics/specific").param("url", validUrl).param("shortUrl", validShortUrl)).andExpect(MockMvcResultMatchers.status().isOk());
    }
    

    //test when requesting the specific analytics with an url that is not in the database
    @Test
    public void test_getSpecificAnalytics_with_url_not_in_db() throws Exception  {
        String validUrl = "bad";
        mockMvc.perform(MockMvcRequestBuilders
        .get("/analytics/specific").param("url", validUrl)).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    //test when requesting the specific analytics with a shortUrl that is not in the database
    @Test
    public void test_getSpecificAnalytics__with_shortUrl_not_in_db() throws Exception  {
        String validShortUrl =  "bad";
        mockMvc.perform(MockMvcRequestBuilders
        .get("/analytics/specific").param("shortUrl", validShortUrl)).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    //test when requesting the specific analytics route without any request params
    @Test
    public void test_getSpecificAnalytics_badReuqest() throws Exception  {
        mockMvc.perform(MockMvcRequestBuilders
        .get("/analytics/specific")).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
