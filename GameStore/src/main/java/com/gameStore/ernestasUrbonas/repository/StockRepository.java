package com.gameStore.ernestasUrbonas.repository;

import com.gameStore.ernestasUrbonas.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    /**
     * Finds a Stock entry by warehouse ID and product ID.
     *
     * @param warehouseId the ID of the warehouse
     * @param productId   the ID of the product
     * @return an Optional containing the Stock if found, or empty if not found
     */
    Optional<Stock> findByWarehouseIdAndProductId(Long warehouseId, Long productId);
}
