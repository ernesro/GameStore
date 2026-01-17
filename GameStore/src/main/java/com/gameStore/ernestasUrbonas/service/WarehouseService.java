package com.gameStore.ernestasUrbonas.service;

import com.gameStore.ernestasUrbonas.dto.WarehouseDTO;
import com.gameStore.ernestasUrbonas.exception.NotFoundException;
import com.gameStore.ernestasUrbonas.mapper.WarehouseMapper;
import com.gameStore.ernestasUrbonas.model.Warehouse;
import com.gameStore.ernestasUrbonas.repository.WarehouseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseService {
    private final WarehouseRepository warehouseRepository;
    private final WarehouseMapper warehouseMapper;

    public WarehouseService(WarehouseRepository warehouseRepository, WarehouseMapper warehouseMapper) {
        this.warehouseRepository = warehouseRepository;
        this.warehouseMapper = warehouseMapper;
    }

    /**
     * Create a new warehouse.
     *
     * @param warehouseDTO Data Transfer Object containing warehouse details.
     * @return The created WarehouseDTO.
     */
    public WarehouseDTO createWarehouse(WarehouseDTO warehouseDTO) {
        Warehouse warehouse = this.warehouseMapper.mapDTOToEntity(warehouseDTO);
        Warehouse savedWarehouse = this.warehouseRepository.save(warehouse);
        return this.warehouseMapper.mapEntityToDTO(savedWarehouse);
    }

    /**
     * Find a warehouse by ID.
     *
     * @param id Warehouse ID.
     * @return WarehouseDTO.
     */
    public WarehouseDTO findWarehouseById(Long id) {
        Warehouse warehouse = this.warehouseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Warehouse not found whit id : " + id));
        return this.warehouseMapper.mapEntityToDTO(warehouse);
    }

    /**
     * Delete a warehouse by ID.
     *
     * @param id Warehouse ID.
     */
    public void deleteWarehouseById(Long id) {
        if (!this.warehouseRepository.existsById(id)) {
            throw new NotFoundException("Warehouse not found with id: " + id);
        }
        this.warehouseRepository.deleteById(id);
    }

    /**
     * Get all warehouses.
     *
     * @return List of WarehouseDTO.
     */
    public List<WarehouseDTO> getAllWarehouses() {
        List <Warehouse> warehouses = warehouseRepository.findAll();
        return warehouses.stream()
                .map(warehouseMapper::mapEntityToDTO)
                .toList();
    }

    /**
     * Update a warehouse.
     *
     * @param id           Warehouse ID.
     * @param warehouseDTO Data Transfer Object containing updated warehouse details.
     * @return The updated WarehouseDTO.
     */
    public WarehouseDTO updateWarehouse(Long id, WarehouseDTO warehouseDTO) {
        Warehouse existingWarehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Warehouse not found with id: " + id));

        existingWarehouse.setName(warehouseDTO.getName());
        existingWarehouse.setLocation(warehouseDTO.getLocation());
        existingWarehouse.setCapacity(warehouseDTO.getCapacity());

        Warehouse updatedWarehouse = warehouseRepository.save(existingWarehouse);
        return warehouseMapper.mapEntityToDTO(updatedWarehouse);
    }
}
