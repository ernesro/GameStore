package com.gameStore.ernestasUrbonas.mapper;

import com.gameStore.ernestasUrbonas.dto.StockDTO;
import com.gameStore.ernestasUrbonas.model.Product;
import com.gameStore.ernestasUrbonas.model.Stock;
import com.gameStore.ernestasUrbonas.model.Warehouse;
import org.springframework.stereotype.Component;

@Component
public class StockMapper {
    public Stock mapDTOToEntity(StockDTO stockDTO, Product product, Warehouse warehouse) {
        Stock stock = new Stock();
        stock.setProduct(product);
        stock.setWarehouse(warehouse);
        stock.setQuantity(stockDTO.getQuantity());
        return stock;
    }

    public StockDTO mapEntityToDTO(Stock stock) {
        return new StockDTO(
                stock.getId(),
                stock.getProduct().getId(),
                stock.getWarehouse().getId(),
                stock.getQuantity()
        );
    }
}
