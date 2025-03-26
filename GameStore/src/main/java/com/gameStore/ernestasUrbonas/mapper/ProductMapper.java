package com.gameStore.ernestasUrbonas.mapper;

import com.gameStore.ernestasUrbonas.dto.ProductDTO;
import com.gameStore.ernestasUrbonas.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product mapDTOToEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setItemCategory(productDTO.getItemCategory());
        product.setItemCondition(productDTO.getItemCondition());
        product.setItemTags(productDTO.getItemTags());
        product.setAverageRating(productDTO.getAverageRating());
        product.setImageUrl(productDTO.getImageUrl());
        product.setStock(productDTO.getStock());
        return product;
    }

    public ProductDTO mapEntityToDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getItemCondition(),
                product.getItemCategory(),
                product.getItemTags(),
                product.getPrice(),
                product.getStock(),
                product.getImageUrl(),
                product.getAverageRating()
        );
    }
}
