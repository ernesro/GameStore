package com.gameStore.ernestasUrbonas.service;

import com.gameStore.ernestasUrbonas.dto.*;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private StockService stockService;

    @InjectMocks
    private OrderService orderService;

    private UserEntity user;
    private Product product;
    private Order order;
    private OrderItem orderItem;
    private OrderRequestDTO requestDTO;
    private OrderResponseDTO responseDTO;

    @BeforeEach
    void setUp() {

        user = new UserEntity();
        user.setId(1L);

        product = new Product();
        product.setId(10L);
        product.setPrice(29.99);

        orderItem = new OrderItem();
        orderItem.setId(100L);
        orderItem.setQuantity(2);

        order = new Order();
        order.setId(1L);
        order.setItems(List.of(orderItem));

        OrderItemRequestDTO itemRequestDTO = new OrderItemRequestDTO();
        itemRequestDTO.setProductId(10L);
        itemRequestDTO.setQuantity(2);

        requestDTO = new OrderRequestDTO();
        requestDTO.setUserId(1L);
        requestDTO.setItems(List.of(itemRequestDTO));
        requestDTO.setWarehouseId(1L);

        responseDTO = new OrderResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setPrice(59.98);
    }


    // =========================================================================
    // createOrder
    // =========================================================================

    @Test
    void createOrder_shouldReturnResponseDTO_whenDataIsValid() {

        //ARRANGE---------------------------------------------------------------------

        StockDTO stockDTO = new StockDTO(1L, 1L, 1L, 99);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(orderMapper.toEntity(requestDTO)).thenReturn(order);
        when(productRepository.findById(10L)).thenReturn(Optional.of(product));
        when(stockService.findStockByWarehouseAndProduct(1L, 10L)).thenReturn(stockDTO);
        when(stockService.updateStockQuantity(10L, 1L, 97)).thenReturn(stockDTO);
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(orderMapper.toResponseDTO(order)).thenReturn(responseDTO);

        //ACT-------------------------------------------------------------------------

        OrderResponseDTO result = orderService.createOrder(requestDTO);

        //ASSERT----------------------------------------------------------------------

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getPrice()).isEqualTo(59.98);

        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void createOrder_shouldCalculateTotalPrice_basedOnProductPriceAndQuantity() {

        //ARRANGE---------------------------------------------------------------------

        orderItem.setQuantity(3); // 3 units × 29.99 = 89.97
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(orderMapper.toEntity(requestDTO)).thenReturn(order);
        when(productRepository.findById(10L)).thenReturn(Optional.of(product));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {

            Order savedOrder = invocation.getArgument(0);
            assertThat(savedOrder.getPrice()).isEqualTo(89.97);
            return savedOrder;
        });
        when(orderMapper.toResponseDTO(any(Order.class))).thenReturn(responseDTO);

        //ACT-------------------------------------------------------------------------

        orderService.createOrder(requestDTO);

        //ASSERT----------------------------------------------------------------------

        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void createOrder_shouldThrowNotFoundException_whenUserDoesNotExist() {

        //ARRANGE---------------------------------------------------------------------

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        //ACT AND ASSERT--------------------------------------------------------------

        assertThatThrownBy(() -> orderService.createOrder(requestDTO))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("User not found");

        verify(orderRepository, never()).save(any());
    }

    @Test
    void createOrder_shouldThrowNotFoundException_whenProductDoesNotExist() {

        //ARRANGE---------------------------------------------------------------------

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(orderMapper.toEntity(requestDTO)).thenReturn(order);
        when(productRepository.findById(10L)).thenReturn(Optional.empty());

        //ACT AND ASSERT--------------------------------------------------------------

        assertThatThrownBy(() -> orderService.createOrder(requestDTO))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Product not found");

        verify(orderRepository, never()).save(any());
    }

    @Test
    void createOrder_shouldThrowNegativeStockException_WhenNewStockIsNegative() {

        //ARRANGE---------------------------------------------------------------------

        StockDTO stockDTO = new StockDTO(1L, 1L, 1L, 0);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(orderMapper.toEntity(requestDTO)).thenReturn(order);
        when(productRepository.findById(10L)).thenReturn(Optional.of(product));
        when(stockService.findStockByWarehouseAndProduct(1L, 10L)).thenReturn(stockDTO);

        //ACT AND ASSERT--------------------------------------------------------------

        assertThatThrownBy(() -> orderService.createOrder(requestDTO))
                .isInstanceOf(NegativeStockException.class)
                .hasMessageContaining("Cannot create the order.");

        verify(orderRepository, never()).save(any());
    }

    @Test
    void createOrder_shouldThrowNotFoundException_whenWarehouseDoesNotExist() {

        //ARRANGE---------------------------------------------------------------------

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(orderMapper.toEntity(requestDTO)).thenReturn(order);
        when(productRepository.findById(10L)).thenReturn(Optional.of(product));
        when(stockService.findStockByWarehouseAndProduct(1L, 10L))
                .thenThrow(new NotFoundException("Warehouse not found"));

        //ACT AND ASSERT--------------------------------------------------------------

        assertThatThrownBy(() -> orderService.createOrder(requestDTO))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Warehouse not found");

        verify(orderRepository, never()).save(any());
    }

    // =========================================================================
    // getAllOrdersByUser
    // =========================================================================

    @Test
    void getAllOrdersByUser_shouldReturnListOfOrders_whenUserHasOrders() {

        //ARRANGE---------------------------------------------------------------------

        List<Order> orders = List.of(order, new Order());
        when(orderRepository.findAllByUserId(1L)).thenReturn(orders);
        when(orderMapper.toResponseDTO(any(Order.class))).thenReturn(responseDTO);

        //ACT-------------------------------------------------------------------------

        List<OrderResponseDTO> result = orderService.getAllOrdersByUser(1L);

        //ASSERT----------------------------------------------------------------------

        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);

        verify(orderRepository, times(1)).findAllByUserId(1L);
    }

    @Test
    void getAllOrdersByUser_shouldReturnEmptyList_whenUserHasNoOrders() {

        //ARRANGE---------------------------------------------------------------------

        when(orderRepository.findAllByUserId(1L)).thenReturn(List.of());

        //ACT-------------------------------------------------------------------------

        List<OrderResponseDTO> result = orderService.getAllOrdersByUser(1L);

        //ASSERT----------------------------------------------------------------------

        assertThat(result).isEmpty();
    }

    // =========================================================================
    // findOrdersById
    // =========================================================================

    @Test
    void findOrdersById_shouldReturnOrder_whenOrderExists() {

        //ARRANGE---------------------------------------------------------------------

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderMapper.toResponseDTO(order)).thenReturn(responseDTO);

        //ACT-------------------------------------------------------------------------

        OrderResponseDTO result = orderService.findOrdersById(1L);

        //ASSERT----------------------------------------------------------------------

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void findOrdersById_shouldThrowNotFoundException_whenOrderDoesNotExist() {

        //ARRANGE---------------------------------------------------------------------

        when(orderRepository.findById(99L)).thenReturn(Optional.empty());

        //ACT AND ASSERT--------------------------------------------------------------

        assertThatThrownBy(() -> orderService.findOrdersById(99L))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("99");
    }

    // =========================================================================
    // updateOrder
    // =========================================================================

    @Test
    void updateOrder_shouldReturnUpdatedOrder_whenOrderExists() {

        //ARRANGE---------------------------------------------------------------------

        OrderItemDTO itemDTO = new OrderItemDTO();
        itemDTO.setId(100L);
        itemDTO.setProductId(10L);
        itemDTO.setQuantity(2);
        itemDTO.setPrice(29.99);

        OrderResponseDTO updateRequest = new OrderResponseDTO();
        updateRequest.setId(1L);
        updateRequest.setPrice(99.99);
        updateRequest.setStatus(OrderStatus.PENDING);
        updateRequest.setItems(List.of(itemDTO));

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderItemRepository.findById(100L)).thenReturn(Optional.of(orderItem));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(orderMapper.toResponseDTO(order)).thenReturn(responseDTO);

        //ACT-------------------------------------------------------------------------

        OrderResponseDTO result = orderService.updateOrder(updateRequest);

        //ASSERT----------------------------------------------------------------------

        assertThat(result).isNotNull();

        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void updateOrder_shouldThrowNotFoundException_whenOrderDoesNotExist() {

        //ARRANGE---------------------------------------------------------------------

        OrderResponseDTO updateRequest = new OrderResponseDTO();
        updateRequest.setId(99L);
        updateRequest.setItems(List.of());
        when(orderRepository.findById(99L)).thenReturn(Optional.empty());

        //ACT AND ASSERT--------------------------------------------------------------

        assertThatThrownBy(() -> orderService.updateOrder(updateRequest))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void updateOrder_shouldThrowNotFoundException_whenOrderItemDoesNotExist() {

        //ARRANGE---------------------------------------------------------------------

        OrderItemDTO itemDTO = new OrderItemDTO();
        itemDTO.setId(999L);

        OrderResponseDTO updateRequest = new OrderResponseDTO();
        updateRequest.setId(1L);
        updateRequest.setItems(List.of(itemDTO));

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderItemRepository.findById(999L)).thenReturn(Optional.empty());

        //ACT AND ASSERT--------------------------------------------------------------

        assertThatThrownBy(() -> orderService.updateOrder(updateRequest))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("999");
    }

    // =========================================================================
    // deleteOrder
    // =========================================================================

    @Test
    void deleteOrder_shouldDeleteOrder_whenOrderExists() {

        //ARRANGE---------------------------------------------------------------------

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        //ACT-------------------------------------------------------------------------

        orderService.deleteOrder(1L);

        //ASSERT----------------------------------------------------------------------

        verify(orderRepository, times(1)).delete(order);
    }

    @Test
    void deleteOrder_shouldThrowNotFoundException_whenOrderDoesNotExist() {

        //ARRANGE---------------------------------------------------------------------

        when(orderRepository.findById(99L)).thenReturn(Optional.empty());

        //ACT AND ASSERT--------------------------------------------------------------

        assertThatThrownBy(() -> orderService.deleteOrder(99L))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("99");

        verify(orderRepository, never()).delete(any());
    }
}

