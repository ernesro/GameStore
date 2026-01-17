package com.gameStore.ernestasUrbonas.repository;

import com.gameStore.ernestasUrbonas.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
