package com.gameStore.ernestasUrbonas.service;

import com.gameStore.ernestasUrbonas.dto.ProductDTO;
import com.gameStore.ernestasUrbonas.exception.NotFoundException;
import com.gameStore.ernestasUrbonas.mapper.ProductMapper;
import com.gameStore.ernestasUrbonas.model.Product;
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

    private final ProductMapper productMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    /**
     * Create a new product.
     *
     * @param productDTO Data Transfer Object containing product details.
     * @return The created ProductDTO.
     */
    public ProductDTO createProduct(ProductDTO productDTO){

        Product product = this.productMapper.mapDTOToEntity(productDTO);
        Product savedProduct = this.productRepository.save(product);
        return this.productMapper.mapEntityToDTO(savedProduct);
    }

    /**
     * Find all products.
     *
     * @return The list of all Products like ProductDTO.
     */
    public List<ProductDTO> findAllProducts(){
        List<Product> products = this.productRepository.findAll();
        return products.stream()
                .map(this.productMapper::mapEntityToDTO)
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
                .orElseThrow(() -> new NotFoundException("Product not found with identifier: " + id));
        return this.productMapper.mapEntityToDTO(product);
    }

    /**
     * Update a product
     *
     * @param updatedProductDTO Data Transfer Object containing updated product details.
     * @return The updated ProductDTO.
     */
    public ProductDTO updateProduct(ProductDTO updatedProductDTO){
        Product existingProduct = this.productRepository.findById(updatedProductDTO.getId())
                .orElseThrow(() -> new NotFoundException("Product not found with identifier: " + updatedProductDTO.getId()));

        existingProduct.setName(updatedProductDTO.getName());
        existingProduct.setDescription(updatedProductDTO.getDescription());
        existingProduct.setPrice(updatedProductDTO.getPrice());
        existingProduct.setItemCategory(updatedProductDTO.getItemCategory());
        existingProduct.setItemCondition(updatedProductDTO.getItemCondition());
        existingProduct.setItemTags(updatedProductDTO.getItemTags());
        existingProduct.setAverageRating(updatedProductDTO.getAverageRating());
        existingProduct.setImageUrl(updatedProductDTO.getImageUrl());

        Product updatedProduct = productRepository.save(existingProduct);
        return this.productMapper.mapEntityToDTO(updatedProduct);
    }

    /**
     * Delete a product by ID.
     *
     * @param id Product ID.
     */
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() ->  new NotFoundException("Product not found with identifier: " + id));
        productRepository.delete(product);
    }
}
