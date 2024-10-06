package com.tienda.tiendaJuegos.controller;

import com.tienda.tiendaJuegos.dto.ProductDTO;
import com.tienda.tiendaJuegos.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    /**
     * Create a new product.
     *
     * @param productDTO Data Transfer Object containing product details for creation.
     * @return The created ProductDTO.
     */
    @Operation(summary = "Create a new product", description = "Creates a new product with the provided details.",
            operationId = "createProduct", tags = { "products" }, responses = {
                    @ApiResponse(responseCode = "200", description = "Product created successfully", content = @Content(
                            mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class))
                    )
            }
    )
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        ProductDTO createdProduct = productService.createProduct(productDTO);
        return ResponseEntity.ok(createdProduct);
    }

    /**
     * Find all products.
     *
     * @return The list of all Products like ProductDTO.
     */
    @Operation(summary = "Find all products", description = "Returns a list of all products.",
            operationId = "findAllProducts", tags = { "products" }, responses = {
                    @ApiResponse(responseCode = "200", description = "List of products found", content = @Content(
                            mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class))
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<ProductDTO>> findAllProducts() {
        return ResponseEntity.ok(productService.findAllProducts());
    }
}
