package com.example.binance_task.repoTests;

import com.example.binance_task.model.Price;
import com.example.binance_task.model.Symbol;
import com.example.binance_task.repository.PriceRepository;
import com.example.binance_task.repository.SymbolRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * PriceRepo tests
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PriceRepositoryTests {

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private SymbolRepository symbolRepository;

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

        testPrice.setIndexPrice(BigDecimal.ONE);
        testPrice.setMarkPrice(BigDecimal.ONE);
        testPrice.setEstimatedSettlePrice(BigDecimal.ONE);
        testPrice.setInterestRate(BigDecimal.ONE);
        testPrice.setLastFundingRate(BigDecimal.ONE);
        testPrice.setSymbol(testSymbol);
        testPrice.setTime(LocalDateTime.now());

        testPrice = priceRepository.save(testPrice);
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
     * check the insert operation
     */
    @Test
    @Transactional
    public void insertTest() {
        long countBefore = priceRepository.count();

        Price savedPrice = priceRepository.save(testPrice);

        assert (countBefore == priceRepository.count() - 1);
        assert (savedPrice.equals(testPrice));

        priceRepository.delete(savedPrice);
    }

    /**
     * check the update operation
     */
    @Test
    @Transactional
    public void updateTest() {
        var savedPrice = priceRepository.save(testPrice);

        savedPrice.setIndexPrice(BigDecimal.TEN);

        var updatedPrice = priceRepository.save(savedPrice);

        assert (Objects.equals(updatedPrice.getSymbol(), savedPrice.getSymbol()));
        assert (Objects.equals(updatedPrice.getIndexPrice(), BigDecimal.TEN));

        priceRepository.delete(savedPrice);
    }

    /**
     * check the select operation
     */
    @Test
    @Transactional
    public void selectTest() {
        assert (!priceRepository.findAll().isEmpty());
    }

    /**
     * check the delete operation
     */
    @Test
    @Transactional
    public void deleteTest() {
        var countBeforeDeleting = priceRepository.count();

        priceRepository.delete(testPrice);

        var newCount = priceRepository.count();

        assert (Objects.equals(countBeforeDeleting - 1, newCount));
    }

    /**
     * findAllByTimeLessThan function test
     */
    @Test
    public void findAllByTimeLessThanTest() {
        LocalDateTime currentTime = LocalDateTime.now();
        var responseList = priceRepository.findAllByTimeLessThan(currentTime);

        assertFalse(responseList.isEmpty(), "Response list should not be empty");

        for (var elem : responseList) {
            assertTrue(elem.getTime().isBefore(currentTime), "Element time is in the future: " + elem.getTime());
        }
    }

    /**
     * findFirstBySymbolOrderByTimeDesc()
     */
    @Test
    public void findFirstBySymbolOrderByTimeDescTest() {
        var response = priceRepository.findFirstBySymbolOrderByTimeDesc(testSymbol).orElseThrow(
                () -> new RuntimeException("Symbol not found exception"));

        assert (testPrice.equals(response));
    }
}
