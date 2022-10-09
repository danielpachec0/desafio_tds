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
public class UrlMapControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testGetUrl() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/{shortUrl}", "3645165")).andExpect(MockMvcResultMatchers.status().isFound());
    }

    @Test
    public void testGetUrl2() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/{shortUrl}", "shortUrlNotInDb"))
                .andExpect(MockMvcResultMatchers
                .status()
                .isNotFound());
    }

    @Test
    public void testPostUrl() throws Exception  {
        String validUrl = "https://google.com";
        mockMvc.perform(MockMvcRequestBuilders.post("/").param("url", validUrl))
        .andExpect(MockMvcResultMatchers
        .status().isOk());
    }

    @Test
    public void testPostUrl2() throws Exception  {
        String invalidUrl = "https://not an url";
        mockMvc.perform(MockMvcRequestBuilders.post("/").param("url", invalidUrl))
        .andExpect(MockMvcResultMatchers
        .status().isBadRequest());
    }
}
