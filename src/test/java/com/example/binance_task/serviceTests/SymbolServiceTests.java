package com.example.binance_task.serviceTests;

import com.example.binance_task.dto.SymbolResponse;
import com.example.binance_task.repository.PriceRepository;
import com.example.binance_task.repository.SymbolRepository;
import com.example.binance_task.service.SymbolService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * PriceService tests
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SymbolServiceTests {

    @Autowired
    private SymbolService symbolService;

    @Autowired
    private SymbolRepository symbolRepository;

    @Autowired
    private PriceRepository priceRepository;

    /**
     * saveSymbolsData function test
     */
    @Test
    public void saveSymbolsDataTest() {
        List<SymbolResponse> responses = new ArrayList<>(2);

        for (int i = 0; i < 2; i++) {
            SymbolResponse response = new SymbolResponse();

            response.setSymbol("test symbol " + i);
            response.setTime(LocalDateTime.now().toInstant(ZoneOffset.ofHours(2)).toEpochMilli());
            response.setInterestRate(BigDecimal.ONE);
            response.setIndexPrice(BigDecimal.ONE);
            response.setMarkPrice(BigDecimal.ONE);
            response.setEstimatedSettlePrice(BigDecimal.ONE);
            response.setLastFundingRate(BigDecimal.ONE);

            responses.add(response);
        }

        long symbolsCountBefore = symbolRepository.count();
        long priceCountBefore = priceRepository.count();

        symbolService.saveSymbolsData(responses);

        assert (symbolRepository.count() == symbolsCountBefore + 2);
        assert (priceRepository.count() == priceCountBefore + 2);

        for (int i = 0; i < 2; i++) {
            var symbolToDelete = symbolRepository.getBySymbol("test symbol " + i).get();
            var priceToDelete = priceRepository.findFirstBySymbolOrderByTimeDesc(symbolToDelete).get();

            priceRepository.delete(priceToDelete);
            symbolRepository.delete(symbolToDelete);
        }
    }

    /**
     * save function test
     *
     * @param symbolName - symbol name
     * @param expectedResult - expected saving result
     */
    @CsvSource({
            "test symbol,true",
            ",false"
    })
    @ParameterizedTest
    public void saveTest(String symbolName, Boolean expectedResult) {
        try {
            var result = symbolService.save(symbolName);
            assert (result != null);

            symbolRepository.delete(result);
        } catch (RuntimeException e) {
            if (expectedResult) {
                System.out.printf("Error in case: symbol name:{%s}", symbolName);
            }
        }
    }

    /**
     * save function test
     *
     * @param symbolName - symbol name
     * @param expectedResult - expected saving result
     */
    @CsvSource({
            "test symbol,true",
            ",false"
    })
    @ParameterizedTest
    public void getTest(String symbolName, Boolean expectedResult) {
        try {
            var result = symbolService.get(symbolName);
            assert (result != null);

            symbolRepository.delete(result);
        } catch (RuntimeException e) {
            if (expectedResult) {
                System.out.printf("Error in case: symbol name:{%s}", symbolName);
            }
        }
    }
}
