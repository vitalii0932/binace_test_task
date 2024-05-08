package com.example.binance_task.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;

/**
 * symbol response from binance
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SymbolResponse {
    private String symbol;
    private BigDecimal markPrice;
    private BigDecimal indexPrice;
    private BigDecimal estimatedSettlePrice;
    private BigDecimal lastFundingRate;
    private BigDecimal interestRate;
    private long nextFundingTime;
    private long time;
}
