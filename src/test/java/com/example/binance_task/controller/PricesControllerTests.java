package com.example.binance_task.controller;

import com.example.binance_task.service.PriceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * PricesController functions test
 */
@SpringBootTest
@AutoConfigureMockMvc
public class PricesControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PriceService priceService;

    /**
     * test get symbol price data from url /api/v1/binance/get_price
     *
     * @throws Exception if something wrong
     */
    @Test
    public void getPriceBySymbolTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/binance/get_price")
                        .param("symbol", "BTCUSDC"))
                .andExpect(status().isOk())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        assert (response.equals(priceService.getLastPrice("BTCUSDC")));
    }
}
