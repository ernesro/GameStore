package com.gameStore.ernestasUrbonas.repository;

import com.gameStore.ernestasUrbonas.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
}

