package com.example.binance_task.job;

import com.example.binance_task.service.PriceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * job for clear old prices
 */
@Component
@RequiredArgsConstructor
@Slf4j
@EnableScheduling
public class ClearOldDataJob {

    private final PriceService priceService;

    /**
     * delete old prices
     */
    @Scheduled(fixedRate = 60000)
    private void clear72After() {
        priceService.clear(LocalDateTime.now().minusHours(72));
    }
}
