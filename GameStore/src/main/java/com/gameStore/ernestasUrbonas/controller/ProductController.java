package com.gameStore.ernestasUrbonas.controller;

import com.gameStore.ernestasUrbonas.dto.ProductDTO;
import com.gameStore.ernestasUrbonas.model.ErrorDetail;
import com.gameStore.ernestasUrbonas.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    /**
     * Create a new product.
     *
     * @param productDTO Data Transfer Object containing product details for creation.
     * @return The created ProductDTO.
     */
    @Operation(
            summary = "Create a new product",
            description = "Creates a new product with the provided details.",
            operationId = "createProduct",
            tags = {"products"},
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Product created successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProductDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid product data provided",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetail.class))
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Product already exists",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetail.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetail.class))
                    )
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        ProductDTO createdProduct = productService.createProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }


    /**
     * Find all products.
     *
     * @return The list of all Products like ProductDTO.
     */
    @Operation(
            summary = "Get all products",
            description = "Returns a list of all product",
            operationId = "findAllProducts",
            tags = {"products"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Products retrieved successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProductDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetail.class))
                    )
            }
    )
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping
    public ResponseEntity<List<ProductDTO>> findAllProducts() {
        return ResponseEntity.ok(productService.findAllProducts());
    }


    /**
     * Find product by ID.
     *
     * @param id Product ID.
     * @return ProductDTO.
     */
    @Operation(
            summary = "Find product by ID",
            description = "Returns a product by ID.",
            operationId = "findProductById",
            tags = {"products"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Product found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProductDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Product not found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetail.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetail.class))
                    )
            }
    )
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findProductById(id));
    }


    /**
     * Update a product.
     *
     * @param updatedProductDTO Data Transfer Object containing updated product details.
     * @return The updated ProductDTO.
     */
    @Operation(
            summary = "Update a product",
            description = "Updates an existing product with the provided details.",
            operationId = "updateProduct",
            tags = {"products"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Product updated successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProductDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid product data provided",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetail.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Product not found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetail.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetail.class))
                    )
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<ProductDTO> updateProduct(@Valid @RequestBody ProductDTO updatedProductDTO) {
        ProductDTO updatedProduct = productService.updateProduct(updatedProductDTO);
        return ResponseEntity.ok(updatedProduct);
    }


    /**
     * Delete a product.
     *
     * @param id Product ID.
     * @return ResponseEntity.
     */
    @Operation(
            summary = "Delete a product",
            description = "Deletes a product by ID.",
            operationId = "deleteProduct",
            tags = {"products"},
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Product deleted successfully"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Product not found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetail.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetail.class))
                    )
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
