package com.gameStore.ernestasUrbonas.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LowStockEvent {
    private Long productId;
    private Long warehouseId;
    private Long stockId;
    private Integer quantity;
}
