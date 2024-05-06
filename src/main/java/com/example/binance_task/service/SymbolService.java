package com.example.binance_task.service;
import com.example.binance_task.dto.SymbolResponse;
import com.example.binance_task.model.Price;
import com.example.binance_task.model.Symbol;
import com.example.binance_task.repository.SymbolRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * service class for Symbol
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SymbolService {

    private final SymbolRepository symbolRepository;
    private final PriceService priceService;

    /**
     * save symbols data from binance
     *
     * @param symbolsData - list of symbols data
     */
    public void saveSymbolsData(List<SymbolResponse> symbolsData) {
        for (var symbolData : symbolsData) {
            try {
                var symbol = get(symbolData.getSymbol());

                Price price = new Price();

                price.setSymbol(symbol);
                price.setMarkPrice(symbolData.getMarkPrice());
                price.setIndexPrice(symbolData.getIndexPrice());
                price.setEstimatedSettlePrice(symbolData.getEstimatedSettlePrice());
                price.setInterestRate(symbolData.getInterestRate());
                price.setLastFundingRate(symbolData.getLastFundingRate());
                price.setTime(LocalDateTime.now());

                priceService.save(price);
            } catch (Exception e) {
                log.error("Symbol: {}, Price: {}, Exception: {}", symbolData.getSymbol(), symbolData.getIndexPrice(), e.getMessage());
            }
        }
    }
}
