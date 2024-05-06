package com.example.binance_task.repository;

import com.example.binance_task.model.Symbol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * repository interface for Symbol entities
 */
public interface SymbolRepository extends JpaRepository<Symbol, Long> {
    /**
     * get Symbol by its name
     *
     * @param symbol - symbol name
     * @return optional symbol
     */
    Optional<Symbol> getBySymbol(String symbol);
}
