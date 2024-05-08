package com.example.binance_task.job;

import com.example.binance_task.dto.SymbolResponse;
import com.example.binance_task.service.SymbolService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

/**
 * job for get symbols data from binance
 */
@Component
@RequiredArgsConstructor
@Slf4j
@EnableScheduling
@Profile("!test")
public class BinanceFetcherJob {

    private final SymbolService symbolService;
    private final RestTemplate restTemplate;

    /**
     * get symbols data and save in db every 30s
     */
    @Scheduled(fixedRate = 30000)
    private void getAndSaveSymbolsData() {
        String url = "https://testnet.binancefuture.com/fapi/v1/premiumIndex";
        ResponseEntity<SymbolResponse[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, SymbolResponse[].class);
        List<SymbolResponse> responses = List.of(Objects.requireNonNull(responseEntity.getBody()));

        if (!responses.isEmpty()) {
            symbolService.saveSymbolsData(responses);
        } else {
            log.error("Response from Binance is Empty");
        }
    }
}
