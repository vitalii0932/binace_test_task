package com.example.binance_task.model;

import jakarta.persistence.*;
import lombok.Data;

/**
 * symbol model
 */
@Data
@Entity
@Table(name = "symbols", uniqueConstraints = {
        @UniqueConstraint(name = "symbol_unique", columnNames = "symbol")
})
public class Symbol {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String symbol;
}