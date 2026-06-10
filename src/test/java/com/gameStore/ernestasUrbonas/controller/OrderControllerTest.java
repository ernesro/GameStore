package com.gameStore.ernestasUrbonas.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gameStore.ernestasUrbonas.dto.OrderRequestDTO;
import com.gameStore.ernestasUrbonas.dto.OrderResponseDTO;
import com.gameStore.ernestasUrbonas.exception.NegativeStockException;
import com.gameStore.ernestasUrbonas.exception.NotFoundException;
import com.gameStore.ernestasUrbonas.model.enums.OrderStatus;
import com.gameStore.ernestasUrbonas.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
@AutoConfigureMockMvc
public class OrderControllerTest {
    @TestConfiguration
    @EnableMethodSecurity
    static class TestSecurityConfig {
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    private OrderResponseDTO orderResponseDTO;
    private OrderRequestDTO orderRequestDTO;

    @BeforeEach
    void setUp() {
        orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setStatus(OrderStatus.PENDING);
        orderResponseDTO.setPrice(9.99D);
        orderResponseDTO.setId(1L);
        orderResponseDTO.setUserId(3L);
        orderResponseDTO.setItems(new ArrayList<>());
        orderResponseDTO.setCreatedAt(LocalDateTime.now());

        orderRequestDTO = new OrderRequestDTO();
        orderRequestDTO.setUserId(3L);
        orderRequestDTO.setWarehouseId(1L);
        orderRequestDTO.setItems(new ArrayList<>());
    }

    @Test
    @WithMockUser(roles = "USER")
    void createNewOrder_shouldReturn201() throws Exception {
        when(orderService.createOrder(any(OrderRequestDTO.class))).thenReturn(orderResponseDTO);

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequestDTO))
                        .with(csrf()))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = "USER")
    void findUserOrders_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/orders").param("userId", "3"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser(roles = "USER")
    void findUserOrders_shouldReturn404() throws Exception {
        when(orderService.getAllOrdersByUser(3L)).thenThrow(new NotFoundException("User not Found"));

        mockMvc.perform(get("/api/orders").param("userId", "3"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "USER")
    void createNewOrder_shouldReturn404() throws Exception {
        when(orderService.createOrder(any(OrderRequestDTO.class)))
                .thenThrow(new NotFoundException("Warehouse not found"));

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequestDTO))
                        .with(csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "USER")
    void createNewOrder_shouldReturn400() throws Exception {
        when(orderService.createOrder(any(OrderRequestDTO.class)))
                .thenThrow(new NegativeStockException("Not enough Stock"));

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequestDTO))
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "USER")
    void updateOrderAsUSER_shouldReturn403() throws Exception {
        mockMvc.perform(put("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequestDTO))
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "USER")
    void updateOrderStatusAsUSER_shouldReturn403() throws Exception {
        mockMvc.perform(put("/api/orders/update-status")
                        .param("orderId", "1")
                        .param("newOrderStatus", "PROCESSING")
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }
}
