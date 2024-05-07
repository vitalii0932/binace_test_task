package com.example.binance_task.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price otherPrice = (Price) o;
        return Objects.equals(symbol, otherPrice.symbol) &&
                Objects.equals(markPrice.doubleValue(), otherPrice.markPrice.doubleValue()) &&
                Objects.equals(indexPrice.doubleValue(), otherPrice.indexPrice.doubleValue()) &&
                Objects.equals(estimatedSettlePrice.doubleValue(), otherPrice.estimatedSettlePrice.doubleValue()) &&
                Objects.equals(lastFundingRate.doubleValue(), otherPrice.lastFundingRate.doubleValue()) &&
                Objects.equals(interestRate.doubleValue(), otherPrice.interestRate.doubleValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol, markPrice, indexPrice, estimatedSettlePrice, lastFundingRate, interestRate);
    }
}
