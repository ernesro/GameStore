package com.gameStore.ernestasUrbonas.controller;

import com.gameStore.ernestasUrbonas.dto.StockDTO;
import com.gameStore.ernestasUrbonas.service.StockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    private final StockService stockService;

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;

    }

    @Operation(summary = "Create a new stock", description = "Creates a new stock with the provided details.",
            operationId = "createStock", tags = { "stocks" }, responses = {
                    @ApiResponse(responseCode = "200", description = "Stock created successfully", content = @Content(
                            mediaType = "application/json", schema = @Schema(implementation = StockDTO.class))
                    )
            }
    )

    @PostMapping
    ResponseEntity<StockDTO> createStock(@Valid @RequestBody StockDTO stockDTO) {
        StockDTO createdStock = stockService.createStock(stockDTO);
        return ResponseEntity.ok(createdStock);
    }
}
