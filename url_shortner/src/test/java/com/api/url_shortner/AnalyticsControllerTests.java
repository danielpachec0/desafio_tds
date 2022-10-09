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

    //test when requesting the specific analytics route with an url or shortUrl that is alredy stored in the database
    @Test
    public void test_getSpecificAnalytics_ok() throws Exception  {
        mockMvc.perform(MockMvcRequestBuilders
        .get("/analytics/{shortUrl}", "testUrlOrShortUrl")).andExpect(MockMvcResultMatchers.status().isOk());
    }

    //test when requesting the specific analytics route with an url or shortUrl that is not alredy stored in the database
    @Test
    public void test_getSpecificAnalytics_notFound() throws Exception  {
        mockMvc.perform(MockMvcRequestBuilders
        .get("/analytics/{shortUrl}", "testUrlOrShortUrl")).andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
