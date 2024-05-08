package com.example.binance_task.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * MainPageLoadingController functions test
 */
@SpringBootTest
@AutoConfigureMockMvc
public class MainPageLoadingControllerTests {

    @Autowired
    private MockMvc mockMvc;

    /**
     * test loading main page from url /api/v1/binance
     *
     * @throws Exception if something wrong
     */
    @Test
    public void loadMainPageTest() throws Exception {
        this.mockMvc.perform(
                        get("/api/v1/binance"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<title>Головна сторінка</title>")));
    }
}
