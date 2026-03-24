package com.gameStore.ernestasUrbonas.service;

import com.gameStore.ernestasUrbonas.dto.OrderRequestDTO;
import com.gameStore.ernestasUrbonas.dto.OrderResponseDTO;
import com.gameStore.ernestasUrbonas.dto.OrderUpdateDTO;
import com.gameStore.ernestasUrbonas.dto.StockDTO;
import com.gameStore.ernestasUrbonas.exception.ForbiddenException;
import com.gameStore.ernestasUrbonas.exception.InvalidOrderStatusTransitionException;
import com.gameStore.ernestasUrbonas.exception.NegativeStockException;
import com.gameStore.ernestasUrbonas.exception.NotFoundException;
import com.gameStore.ernestasUrbonas.mapper.OrderMapper;
import com.gameStore.ernestasUrbonas.model.Order;
import com.gameStore.ernestasUrbonas.model.OrderItem;
import com.gameStore.ernestasUrbonas.model.Product;
import com.gameStore.ernestasUrbonas.model.UserEntity;
import com.gameStore.ernestasUrbonas.model.enums.OrderStatus;
import com.gameStore.ernestasUrbonas.repository.OrderItemRepository;
import com.gameStore.ernestasUrbonas.repository.OrderRepository;
import com.gameStore.ernestasUrbonas.repository.ProductRepository;
import com.gameStore.ernestasUrbonas.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final StockService stockService;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    @Autowired
    public OrderService(StockService stockService, OrderRepository orderRepository, OrderItemRepository orderItemRepository, UserRepository userRepository, ProductRepository productRepository, OrderMapper orderMapper) {
        this.stockService = stockService;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.orderMapper = orderMapper;
    }

    /**
     * Create a new order.
     *
     * @param dto Data Transfer Object containing order details.
     * @return The created OrderDTO.
     */
    @Transactional
    public OrderResponseDTO createOrder(OrderRequestDTO dto) {
        UserEntity user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        Order order = orderMapper.toEntity(dto);
        order.setUser(user);

        double total = 0;
        for (int i = 0; i < dto.getItems().size(); i++) {
            Product product = productRepository.findById(dto.getItems().get(i).getProductId())
                    .orElseThrow(() -> new NotFoundException("Product not found"));

            OrderItem item = order.getItems().get(i);

            StockDTO itemStock = stockService.findStockByWarehouseAndProduct(dto.getWarehouseId(), product.getId());
            int newStock = itemStock.getQuantity() - item.getQuantity();

            if (newStock < 0)
                throw new NegativeStockException
                        ("Cannot create the order. Actual Stock for " + product.getName()
                                + " is " + itemStock.getQuantity() + " and the order needs " + item.getQuantity());

            stockService.updateStockQuantity(product.getId(), dto.getWarehouseId(), newStock);

            item.setProduct(product);
            item.setOrder(order);
            item.setPrice(product.getPrice());

            total += product.getPrice() * item.getQuantity();
        }

        order.setPrice(total);
        return orderMapper.toResponseDTO(orderRepository.save(order));
    }

    /**
     * Find all user orders.
     *
     * @return The list of all Orders as OrderResponseDTO.
     */
    @Transactional
    public List<OrderResponseDTO> getAllOrdersByUser(Long userId) {
        List<Order> orders = this.orderRepository.findAllByUserId(userId);
        return orders.stream()
                .map(this.orderMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get a order by ID.
     *
     * @param id Order ID.
     * @return OrderDTO.
     */
    public OrderResponseDTO findOrdersById(Long id) {
        Order order = this.orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found with identifier: " + id));
        return this.orderMapper.toResponseDTO(order);
    }

    /**
     * Update a order
     *
     * @param orderUpdateDTO Data Transfer Object containing updated order details.
     * @return The updated OrderDTO.
     */

    @Transactional
    public OrderResponseDTO updateOrder(OrderUpdateDTO orderUpdateDTO) {
        Order existingOrder = this.orderRepository.findById(orderUpdateDTO.getId())
                .orElseThrow(() -> new NotFoundException("Order not found with identifier: " + orderUpdateDTO.getId()));

        if (!existingOrder.getStatus().equals(OrderStatus.PENDING)) {
            throw new ForbiddenException("Can not update an Order that is not PENDING state.");
        }

        existingOrder.setPrice(orderUpdateDTO.getPrice());

        existingOrder.setItems(
                orderUpdateDTO
                        .getItems()
                        .stream()
                        .map(orderItemDTO -> orderItemRepository.findById(orderItemDTO.getId())
                                .orElseThrow(() -> new NotFoundException("OrderItem not found with id: " + orderItemDTO.getId())))
                        .collect(Collectors.toList())
        );

        Order updatedOrder = orderRepository.save(existingOrder);
        return this.orderMapper.toResponseDTO(updatedOrder);
    }

    /**
     * Update order status
     *
     * @param orderId   Order ID
     * @param newStatus New status for the Order
     * @return The updated OrderDTO.
     */
    @Transactional
    public OrderResponseDTO updateOrderStatus(Long orderId, OrderStatus newStatus) {
        Order existingOrder = this.orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found with identifier: " + orderId));

        if (existingOrder.getStatus().canTransitionTo(newStatus)) {
            existingOrder.setStatus(newStatus);
        } else {
            throw new InvalidOrderStatusTransitionException("Invalid advance from " + existingOrder.getStatus() +
                    " to " + newStatus);
        }

        Order updatedOrder = orderRepository.save(existingOrder);
        return this.orderMapper.toResponseDTO(updatedOrder);
    }

    /**
     * Delete a order by ID.
     *
     * @param id Order ID.
     */
    @Transactional
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found with identifier: " + id));

        orderRepository.delete(order);
    }

}
