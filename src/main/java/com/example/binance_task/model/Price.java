package com.example.binance_task.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * model for symbols prices history
 */
@Data
@Entity
@IdClass(PriceId.class)
@Table(name = "prices")
public class Price {
    @Id
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Symbol symbol;
    @Id
    private LocalDateTime time;

    @Column(precision = 15, scale = 7)
    private BigDecimal markPrice;
    @Column(precision = 15, scale = 7)
    private BigDecimal indexPrice;
    @Column(precision = 15, scale = 7)
    private BigDecimal estimatedSettlePrice;
    @Column(precision = 15, scale = 7)
    private BigDecimal lastFundingRate;
    @Column(precision = 15, scale = 7)
    private BigDecimal interestRate;
}
