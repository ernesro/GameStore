package com.gameStore.ernestasUrbonas.dto;

import com.gameStore.ernestasUrbonas.model.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {

    private Long id;
    private double price;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private List<OrderItemDTO> items;
}