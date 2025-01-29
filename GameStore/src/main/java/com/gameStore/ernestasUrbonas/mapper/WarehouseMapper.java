package com.gameStore.ernestasUrbonas.mapper;

import com.gameStore.ernestasUrbonas.dto.WarehouseDTO;
import com.gameStore.ernestasUrbonas.model.Warehouse;

public class WarehouseMapper {
    public Warehouse mapDTOToEntity(WarehouseDTO warehouseDTO) {
        Warehouse warehouse = new Warehouse();
        warehouse.setName(warehouseDTO.getName());
        warehouse.setLocation(warehouseDTO.getLocation());
        warehouse.setCapacity(warehouseDTO.getCapacity());
        return warehouse;
    }

    public WarehouseDTO mapEntityToDTO(Warehouse warehouse) {
        return new WarehouseDTO(
                warehouse.getId(),
                warehouse.getName(),
                warehouse.getLocation(),
                warehouse.getCapacity()
        );
    }
}
