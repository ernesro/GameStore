package com.gameStore.ernestasUrbonas.repository;

import com.gameStore.ernestasUrbonas.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
