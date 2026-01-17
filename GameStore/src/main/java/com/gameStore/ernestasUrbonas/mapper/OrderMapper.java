package com.gameStore.ernestasUrbonas.mapper;

import com.gameStore.ernestasUrbonas.dto.OrderItemDTO;
import com.gameStore.ernestasUrbonas.dto.OrderRequestDTO;
import com.gameStore.ernestasUrbonas.dto.OrderResponseDTO;
import com.gameStore.ernestasUrbonas.model.Order;
import com.gameStore.ernestasUrbonas.model.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    public Order toEntity(OrderRequestDTO dto) {
        Order order = new Order();
        order.setCreatedAt(LocalDateTime.now());

        List<OrderItem> items = dto.getItems().stream().map(itemDto -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setQuantity(itemDto.getQuantity());
            orderItem.setOrder(order);
            return orderItem;
        }).collect(Collectors.toList());

        order.setItems(items);

        return order;
    }

    public OrderResponseDTO toResponseDTO(Order order) {
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setId(order.getId());
        dto.setUserId(order.getUser().getId());
        dto.setStatus(order.getStatus());
        dto.setCreatedAt(order.getCreatedAt());
        dto.setPrice(order.getPrice());

        dto.setItems(order.getItems().stream().map(item -> {
            OrderItemDTO itemDTO = new OrderItemDTO();
            itemDTO.setProductId(item.getProduct().getId());
            itemDTO.setQuantity(item.getQuantity());
            itemDTO.setPrice(item.getPrice());
            return itemDTO;
        }).collect(Collectors.toList()));

        return dto;
    }
}
