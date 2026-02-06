package com.gameStore.ernestasUrbonas.service;

import com.gameStore.ernestasUrbonas.dto.OrderRequestDTO;
import com.gameStore.ernestasUrbonas.dto.OrderResponseDTO;
import com.gameStore.ernestasUrbonas.exception.NotFoundException;
import com.gameStore.ernestasUrbonas.mapper.OrderMapper;
import com.gameStore.ernestasUrbonas.model.Order;
import com.gameStore.ernestasUrbonas.model.UserEntity;
import com.gameStore.ernestasUrbonas.repository.OrderRepository;
import com.gameStore.ernestasUrbonas.repository.UserRepository;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderService orderService;

    @Test
    void shouldCreateOrderSuccessfully() {
        // GIVEN
        OrderRequestDTO requestDTO = new OrderRequestDTO();
        requestDTO.setUserId(1L);
        requestDTO.setItems(List.of());

        UserEntity user = new UserEntity();
        user.setId(1L);

        Order orderEntity = new Order();
        Order savedOrder = new Order();
        savedOrder.setId(10L);

        OrderResponseDTO responseDTO = new OrderResponseDTO();
        responseDTO.setId(10L);

        // WHEN
        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(orderMapper.toEntity(requestDTO))
                .thenReturn(orderEntity);

        when(orderRepository.save(orderEntity))
                .thenReturn(savedOrder);

        when(orderMapper.toResponseDTO(savedOrder))
                .thenReturn(responseDTO);

        // THEN
        OrderResponseDTO result = orderService.createOrder(requestDTO);

        // ASSERT
        assertNotNull(result);
        assertEquals(10L, result.getId());

        verify(orderRepository).save(orderEntity);
    }


    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        OrderRequestDTO requestDTO = new OrderRequestDTO();
        requestDTO.setUserId(99L);

        when(userRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> orderService.createOrder(requestDTO)
        );
    }

}
