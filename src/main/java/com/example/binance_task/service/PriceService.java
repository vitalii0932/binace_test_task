package com.example.binance_task.service;

import com.example.binance_task.model.Price;
import com.example.binance_task.repository.PriceRepository;
import com.example.binance_task.repository.SymbolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * service class for Price
 */
@Service
@RequiredArgsConstructor
public class PriceService {

    private final PriceRepository priceRepository;
    private final SymbolRepository symbolRepository;

    /**
     * get last price from some symbol name
     *
     * @param symbolName - symbol name
     * @return string with price data
     * @throws RuntimeException if something wrong
     */
    public String getLastPrice(String symbolName) throws RuntimeException {
        var symbol = symbolRepository.getBySymbol(symbolName).orElseThrow(
                () -> new RuntimeException("Symbol not found exception")
        );
        var price = priceRepository.findFirstBySymbolOrderByTimeDesc(symbol).orElseThrow(
                () -> new RuntimeException("This symbol doesnt have prices now. You can try later.")
        );

        return String.format("""
        Symbol: %s
        IndexPrice: %s
        MarkPrice: %s
        EstimatedSettlePrice: %s
        InterestRate: %s
        LastFundingRate: %s
        """, symbolName, price.getIndexPrice(), price.getMarkPrice(), price.getEstimatedSettlePrice(), price.getInterestRate(), price.getLastFundingRate());
    }

}
