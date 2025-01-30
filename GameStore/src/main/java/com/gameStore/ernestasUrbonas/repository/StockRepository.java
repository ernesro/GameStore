package com.gameStore.ernestasUrbonas.repository;

import com.gameStore.ernestasUrbonas.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
}
