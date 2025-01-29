package com.gameStore.ernestasUrbonas.dto;

import com.gameStore.ernestasUrbonas.model.Product;
import com.gameStore.ernestasUrbonas.model.Warehouse;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockDTO {

    private Long id;

    @NotNull(message = "Product ID cannot be null")
    private Product product;

    @NotNull(message = "Warehouse ID cannot be null")
    private Warehouse warehouse;

    @NotNull(message = "Stock cannot be null")
    @Size(message = "Stock must have at least 0 quantity")
    private Integer quantity;
}
