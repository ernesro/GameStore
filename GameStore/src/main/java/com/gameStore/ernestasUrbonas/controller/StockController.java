package com.gameStore.ernestasUrbonas.controller;

import com.gameStore.ernestasUrbonas.dto.StockDTO;
import com.gameStore.ernestasUrbonas.service.StockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    private final StockService stockService;

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;

    }


    /**
     * Create a new stock.
     *
     * @param stockDTO Data Transfer Object containing stock details for creation.
     * @return The created StockDTO.
     */
    @Operation(
            summary = "Create a new stock",
            description = "Creates a new stock with the provided details.",
            operationId = "createStock",
            tags = { "stocks" },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Stock created successfully",
                            content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = StockDTO.class)
                            )
                    )
            }
    )

    @PostMapping
    ResponseEntity<StockDTO> createStock(@Valid @RequestBody StockDTO stockDTO) {
        StockDTO createdStock = stockService.createStock(stockDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStock);
    }


    /**
     * Finds stock per warehouse and product IDs.
     *
     * @param warehouseId ID of the warehouse.
     * @param productId ID of the product.
     * @return StockDTO containing stock details.
     */
    @Operation(
            summary = "Find stock by warehouse and product IDs",
            description = "Retrieves stock details for a specific warehouse and product combination.",
            operationId = "findStockByWarehouseAndProduct",
            tags = { "stocks" },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Stock found successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = StockDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Stock not found"
                    )
            }
    )
    @GetMapping("/search")
    ResponseEntity<StockDTO> findStockByWarehouseAndProduct(@RequestParam Long warehouseId, @RequestParam Long productId) {
        StockDTO stockDTO = stockService.findStockByWarehouseAndProduct(warehouseId, productId);
        return ResponseEntity.ok(stockDTO);
    }

    /**
     * Update stock quantity.
     *
     * @param productId ID of the product.
     * @param warehouseId ID of the warehouse.
     * @param quantity New quantity to be set.
     * @return Updated StockDTO.
     */
    @Operation(
            summary = "Update stock quantity",
            description = "Updates the stock quantity for a specific product in a specific warehouse.",
            operationId = "updateStockQuantity",
            tags = { "stocks" },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Stock quantity updated successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = StockDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Stock not found"
                    )
            }
    )
    @PutMapping("/updateQuantity")
    ResponseEntity<StockDTO> updateStockQuantity(@RequestParam Long productId,
                                                 @RequestParam Long warehouseId,
                                                 @RequestParam Integer quantity) {
        StockDTO updatedStock = stockService.updateStockQuantity(productId, warehouseId, quantity);
        return ResponseEntity.ok(updatedStock);
    }
}
