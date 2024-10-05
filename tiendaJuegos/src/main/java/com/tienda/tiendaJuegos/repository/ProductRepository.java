package com.tienda.tiendaJuegos.repository;

import com.tienda.tiendaJuegos.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
