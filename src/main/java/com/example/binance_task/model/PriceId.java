package com.example.binance_task.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * price id with symbol id and time
 */
@Data
@Embeddable
public class PriceId implements Serializable {
    private Long symbol;
    private LocalDateTime time;
}
