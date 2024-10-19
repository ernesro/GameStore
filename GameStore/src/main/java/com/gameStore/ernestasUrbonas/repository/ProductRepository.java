package com.gameStore.ernestasUrbonas.repository;

import com.gameStore.ernestasUrbonas.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
