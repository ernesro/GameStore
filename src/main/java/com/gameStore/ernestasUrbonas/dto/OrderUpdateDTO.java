package com.gameStore.ernestasUrbonas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderUpdateDTO {
    private Long id;
    private double price;
    private List<OrderItemDTO> items;
}
