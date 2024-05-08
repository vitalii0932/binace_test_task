package com.example.binance_task.repoTests;

import com.example.binance_task.model.Symbol;
import com.example.binance_task.repository.SymbolRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Objects;

/**
 * SymbolRepo tests
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SymbolRepositoryTests {

    @Autowired
    private SymbolRepository symbolRepository;

    private Symbol testSymbol;

    /**
     * set the test symbol before all the tests
     */
    @BeforeEach
    public void setUpTheTestSymbol() {
        testSymbol = new Symbol();
        testSymbol.setSymbol("test");
    }

    /**
     * delete the test symbol
     */
    @AfterEach
    public void deleteTheTestSymbol() {
        symbolRepository.delete(testSymbol);
    }

    /**
     * check the insert operation
     */
    @Test
    @Transactional
    public void insertTest() {
        long countBefore = symbolRepository.count();

        Symbol savedSymbol = symbolRepository.save(testSymbol);

        assert (countBefore == symbolRepository.count() - 1);
        assert (savedSymbol.equals(testSymbol));

        symbolRepository.delete(savedSymbol);
    }

    /**
     * check the update operation
     */
    @Test
    @Transactional
    public void updateTest() {
        var savedSymbol = symbolRepository.save(testSymbol);

        savedSymbol.setSymbol("new test symbol");

        var updatedSymbol = symbolRepository.save(savedSymbol);

        assert (Objects.equals(updatedSymbol.getSymbol(), savedSymbol.getSymbol()));

        symbolRepository.delete(savedSymbol);
    }

    /**
     * check the select operation
     */
    @Test
    @Transactional
    public void selectTest() {
        assert (!symbolRepository.findAll().isEmpty());
    }

    /**
     * check the delete operation
     */
    @Test
    @Transactional
    public void deleteTest() {
        var countBeforeDeleting = symbolRepository.count();

        symbolRepository.delete(testSymbol);

        var newCount = symbolRepository.count();

        assert (Objects.equals(countBeforeDeleting - 1, newCount));
    }

    /**
     * getBySymbol function test
     */
    @Test
    public void getBySymbolTest() {
        symbolRepository.save(testSymbol);

        var response = symbolRepository.getBySymbol(testSymbol.getSymbol());

        assert (response.orElseThrow(() -> new RuntimeException("Symbol not found exception")).getSymbol().equals(testSymbol.getSymbol()));
    }
}
