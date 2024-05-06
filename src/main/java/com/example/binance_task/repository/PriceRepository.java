package com.example.binance_task.repository;

import com.example.binance_task.model.Price;
import com.example.binance_task.model.Symbol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * repository interface for Price entities
 */
public interface PriceRepository extends JpaRepository<Price, Long> {
    /**
     * finds all prices with a time less than the specified time
     *
     * @param time - the time limit
     * @return a list of prices
     */
    List<Price> findAllByTimeLessThan(LocalDateTime time);

    /**
     * get last price from some symbol
     *
     * @param symbol - symbol entity
     * @return optional price
     */
    Optional<Price> findFirstBySymbolOrderByTimeDesc(Symbol symbol);
}
