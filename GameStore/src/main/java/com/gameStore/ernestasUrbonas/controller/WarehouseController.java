package com.gameStore.ernestasUrbonas.controller;

import com.gameStore.ernestasUrbonas.dto.WarehouseDTO;
import com.gameStore.ernestasUrbonas.model.ErrorDetail;
import com.gameStore.ernestasUrbonas.service.WarehouseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warehouses")
public class WarehouseController {
   private final WarehouseService warehouseService;

   @Autowired
   public WarehouseController(WarehouseService warehouseService) {
       this.warehouseService = warehouseService;
   }



    /**
     * Create a new warehouse.
     *
     * @param warehouseDTO Data Transfer Object containing warehouse details for creation.
     * @return The created WarehouseDTO.
     */
    @Operation(
            summary = "Create a new warehouse",
            description = "Creates a new warehouse with the provided details.",
            operationId = "createWarehouse",
            tags = { "warehouses" },
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Warehouse created successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = WarehouseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid warehouse data provided",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetail.class))
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Warehouse already exists",
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
    @PostMapping
    public ResponseEntity<WarehouseDTO> creteWarehouse (@Valid @RequestBody WarehouseDTO warehouseDTO) {
        WarehouseDTO created = warehouseService.createWarehouse(warehouseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }



    /**
     * Get all warehouses.
     *
     * @return List of WarehouseDTO.
     */
    @Operation(
            summary = "Get all warehouses",
            description = "Returns a list of all warehouses.",
            operationId = "getAllWarehouses",
            tags = { "warehouses" },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of warehouses found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = WarehouseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetail.class))
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<WarehouseDTO>> getWarehouseById() {
        return ResponseEntity.ok(warehouseService.getAllWarehouses());
    }


    /**
     * Find warehouse by ID.
     *
     * @param id Warehouse ID.
     * @return WarehouseDTO.
     */
    @Operation(
            summary = "Find warehouse by ID",
            description = "Returns a warehouse by ID.",
            operationId = "findWarehouseById",
            tags = { "warehouses" },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Warehouse found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = WarehouseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Warehouse not found",
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
    @GetMapping("/{id}")
    public ResponseEntity<WarehouseDTO> findWarehouseById(@PathVariable Long id) {
        WarehouseDTO warehouseDTO = warehouseService.findWarehouseById(id);
        return ResponseEntity.ok(warehouseDTO);
    }



    /**
     * Update a warehouse.
     *
     * @param id           Warehouse ID.
     * @param warehouseDTO Data Transfer Object containing updated warehouse details.
     * @return The updated WarehouseDTO.
     */
    @Operation(
            summary = "Update a warehouse",
            description = "Updates a warehouse with the provided details.",
            operationId = "updateWarehouse",
            tags = { "warehouses" },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Warehouse updated successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = WarehouseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid warehouse data provided",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetail.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Warehouse not found",
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
    @PutMapping("/{id}")
    public ResponseEntity<WarehouseDTO> updateWarehouse(@PathVariable Long id, @Valid @RequestBody WarehouseDTO warehouseDTO) {
        warehouseDTO.setId(id);
        WarehouseDTO updatedWarehouse = warehouseService.updateWarehouse(id, warehouseDTO);
        return ResponseEntity.ok(updatedWarehouse);
    }



    /**
     * Delete a warehouse by ID.
     *
     * @param id Warehouse ID.
     */
    @Operation(
            summary = "Delete a warehouse by ID",
            description = "Deletes a warehouse by its ID.",
            operationId = "deleteWarehouseById",
            tags = { "warehouses" },
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Warehouse deleted successfully"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Warehouse not found",
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
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWarehouseById(@PathVariable Long id) {
        warehouseService.deleteWarehouseById(id);
        return ResponseEntity.noContent().build();
    }
}
