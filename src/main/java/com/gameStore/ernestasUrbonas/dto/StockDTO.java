package com.gameStore.ernestasUrbonas.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockDTO {

    private Long id;

    @NotNull(message = "Product ID cannot be null")
    private Long idProduct;

    @NotNull(message = "Warehouse ID cannot be null")
    private Long idWarehouse;

    @NotNull(message = "Stock cannot be null")
    @Min(value = 0, message = "Stock must be greater than or equal to 0")
    private Integer quantity;
}
