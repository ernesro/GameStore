package com.gameStore.ernestasUrbonas.service;

import com.gameStore.ernestasUrbonas.model.Product;
import com.gameStore.ernestasUrbonas.dto.ProductDTO;
import com.gameStore.ernestasUrbonas.exception.EntityNotFoundException;
import com.gameStore.ernestasUrbonas.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer for managing products.
 */
@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Create a new product.
     *
     * @param productDTO Data Transfer Object containing product details.
     * @return The created ProductDTO.
     */
    public ProductDTO createProduct(ProductDTO productDTO){
        Product product = this.mapDTOToEntity(productDTO);
        Product savedProduct = this.productRepository.save(product);
        return this.mapEntityToDTO(savedProduct);
    }

    /**
     * Find all products.
     *
     * @return The list of all Products like ProductDTO.
     */
    public List<ProductDTO> findAllProducts(){
        List<Product> products = this.productRepository.findAll();
        return products.stream()
                .map(this::mapEntityToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get a product by ID.
     *
     * @param id Product ID.
     * @return ProductDTO.
     */
    public ProductDTO findProductById(Long id) {
        Product product = this.productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product", id));
        return this.mapEntityToDTO(product);
    }

    /**
     * Update a product
     *
     * @param updatedProductDTO Data Transfer Object containing updated product details.
     * @return The updated ProductDTO.
     */
    public ProductDTO updateProduct(ProductDTO updatedProductDTO){
        Product existingProduct = this.productRepository.findById(updatedProductDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Product", updatedProductDTO.getId()));

        existingProduct.setName(updatedProductDTO.getName());
        existingProduct.setDescription(updatedProductDTO.getDescription());
        existingProduct.setPrice(updatedProductDTO.getPrice());
        existingProduct.setCategory(updatedProductDTO.getCategory());
        existingProduct.setCondition(updatedProductDTO.getCondition());
        existingProduct.setTags(updatedProductDTO.getTags());
        existingProduct.setAverageRating(updatedProductDTO.getAverageRating());
        existingProduct.setImageUrl(updatedProductDTO.getImageUrl());
        existingProduct.setStock(updatedProductDTO.getStock());

        Product updatedProduct = productRepository.save(existingProduct);
        return mapEntityToDTO(updatedProduct);
    }

    /**
     * Delete a product by ID.
     *
     * @param id Product ID.
     */
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product", id));
        productRepository.delete(product);
    }

    private Product mapDTOToEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setCategory(productDTO.getCategory());
        product.setCondition(productDTO.getCondition());
        product.setTags(productDTO.getTags());
        product.setAverageRating(productDTO.getAverageRating());
        product.setImageUrl(productDTO.getImageUrl());
        product.setStock(productDTO.getStock());
        return product;
    }

    private ProductDTO mapEntityToDTO(Product product) {
            return new ProductDTO(
                    product.getId(),
                    product.getName(),
                    product.getDescription(),
                    product.getCondition(),
                    product.getCategory(),
                    product.getTags(),
                    product.getPrice(),
                    product.getStock(),
                    product.getImageUrl(),
                    product.getAverageRating()
            );
    }

}
