package com.example.binance_task.serviceTests;

import com.example.binance_task.model.Price;
import com.example.binance_task.model.Symbol;
import com.example.binance_task.repository.PriceRepository;
import com.example.binance_task.repository.SymbolRepository;
import com.example.binance_task.service.PriceService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * PriceService tests
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PriceServiceTests {

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private SymbolRepository symbolRepository;

    @Autowired
    private PriceService priceService;

    private Price testPrice;

    private Symbol testSymbol;

    /**
     * set the test prices and symbols before all the tests
     */
    @BeforeEach
    public void setUpTheTestPriceAndSymbol() {
        testPrice = new Price();
        testSymbol = new Symbol();

        testSymbol.setSymbol("test");

        testSymbol = symbolRepository.save(testSymbol);

        testPrice.setIndexPrice(BigDecimal.valueOf(1.0000001));
        testPrice.setMarkPrice(BigDecimal.valueOf(1.0000001));
        testPrice.setEstimatedSettlePrice(BigDecimal.valueOf(1.0000001));
        testPrice.setInterestRate(BigDecimal.valueOf(1.0000001));
        testPrice.setLastFundingRate(BigDecimal.valueOf(1.0000001));
        testPrice.setSymbol(testSymbol);
        testPrice.setTime(LocalDateTime.now());
    }

    /**
     * delete all the test prices and symbols
     */
    @AfterEach
    public void deleteTheTestPriceAndSymbol() {
        priceRepository.delete(testPrice);
        symbolRepository.delete(testSymbol);
    }

    /**
     * getLastPrice function test
     */
    @Test
    public void getLastPriceTest() {
        priceRepository.save(testPrice);

        assert (priceService.getLastPrice(testSymbol.getSymbol()).equals(
                String.format("""
        Symbol: %s
        IndexPrice: %s
        MarkPrice: %s
        EstimatedSettlePrice: %s
        InterestRate: %s
        LastFundingRate: %s
        """, testSymbol.getSymbol(), testPrice.getIndexPrice(), testPrice.getMarkPrice(), testPrice.getEstimatedSettlePrice(), testPrice.getInterestRate(), testPrice.getLastFundingRate())
        ));
    }

    /**
     * save function test
     */
    @Test
    public void saveTest() {
        assert (testPrice.equals(priceRepository.save(testPrice)));
    }

    /**
     * clear function test
     */
    @Test
    public void clearTest() {
        priceService.clear(LocalDateTime.now());

        assert (priceRepository.count() == 0);
    }
}
