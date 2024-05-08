package com.example.binance_task.controller;

import com.example.binance_task.service.PriceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * controller for getting symbols prices
 */
@RestController
@RequestMapping("/api/v1/binance")
@RequiredArgsConstructor
public class PricesController {

    private final PriceService priceService;

    /**
     * get price by some symbol
     *
     * @param symbolName - symbol name
     * @return a response with body with some prices or exception text
     */
    @Operation(summary = "Get price for some symbol")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully obtaining a price."),
            @ApiResponse(responseCode = "403", description = "Error during price retrieval.")
    })
    @GetMapping("/get_price")
    public ResponseEntity<?> getPriceBySymbol(@RequestParam(name = "symbol") String symbolName) {
        try {
            return ResponseEntity.ok(priceService.getLastPrice(symbolName));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
        }
    }
}
