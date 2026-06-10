package com.gameStore.ernestasUrbonas.controller;

import com.gameStore.ernestasUrbonas.dto.OrderRequestDTO;
import com.gameStore.ernestasUrbonas.dto.OrderResponseDTO;
import com.gameStore.ernestasUrbonas.dto.OrderUpdateDTO;
import com.gameStore.ernestasUrbonas.model.ErrorDetail;
import com.gameStore.ernestasUrbonas.model.enums.OrderStatus;
import com.gameStore.ernestasUrbonas.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Create a new order.
     *
     * @param orderRequestDTO Data Transfer Object containing order details for creation.
     * @return The created OrderResponseDTO.
     */
    @Operation(
            summary = "Create a new order",
            description = "Creates a new order with the provided details.",
            operationId = "createOrder",
            tags = {"orders"},
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Order created successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = OrderResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid order data provided",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetail.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User or Product not found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetail.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetail.class))
                    )
            }
    )
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        OrderResponseDTO createdOrderDTO = orderService.createOrder(orderRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrderDTO);
    }

    /**
     * Get all orders for a specific user.
     *
     * @param userId ID of the user whose orders are to be retrieved.
     * @return List of OrderResponseDTO.
     */
    @Operation(
            summary = "Get all orders for a specific user",
            description = "Retrieves all orders associated with the specified user ID.",
            operationId = "getAllUserOrders",
            tags = {"orders"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Orders retrieved successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = OrderResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User not found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetail.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetail.class))
                    )
            }
    )
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getAllUserOrders(@RequestParam Long userId) {
        return ResponseEntity.ok(orderService.getAllOrdersByUser(userId));
    }

    /**
     * Update order
     *
     * @param orderUpdateDTO DTO of the order updated.
     * @return A OrderResponseDTO.
     */
    @Operation(
            summary = "Updates the order",
            description = "Updates price and OrderItems.",
            operationId = "updateOrder",
            tags = {"orders"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Orders updated successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = OrderResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid order data provided",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetail.class))
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Order is not in PENDING status",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetail.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Order not found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetail.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetail.class))
                    ),
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<OrderResponseDTO> updateOrder(@RequestBody OrderUpdateDTO orderUpdateDTO) {
        return ResponseEntity.ok(orderService.updateOrder(orderUpdateDTO));
    }

    /**
     * Update order Status
     *
     * @param orderId        order ID.
     * @param newOrderStatus new order Status
     * @return A OrderResponseDTO.
     */
    @Operation(
            summary = "Updates the order Status",
            description = "Updates the order Status",
            operationId = "updateOrderStatus",
            tags = {"orders"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OrderStatus updated successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = OrderResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid order data provided",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetail.class))
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Invalid order status transition",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetail.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Order not found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetail.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetail.class))
                    ),
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update-status")
    public ResponseEntity<OrderResponseDTO> updateOrderStatus(@RequestParam Long orderId, OrderStatus newOrderStatus) {
        return ResponseEntity.ok(orderService.updateOrderStatus(orderId, newOrderStatus));
    }
}
