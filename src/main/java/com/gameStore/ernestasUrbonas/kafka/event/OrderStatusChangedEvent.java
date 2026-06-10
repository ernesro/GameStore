package com.gameStore.ernestasUrbonas.kafka.event;

import com.gameStore.ernestasUrbonas.model.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusChangedEvent {
    private Long orderId;
    private Long userId;
    private OrderStatus orderNewStatus;
}
