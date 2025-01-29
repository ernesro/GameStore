package com.gameStore.ernestasUrbonas.mapper;

import com.gameStore.ernestasUrbonas.dto.ProductDTO;
import com.gameStore.ernestasUrbonas.dto.StockDTO;
import com.gameStore.ernestasUrbonas.model.Product;
import com.gameStore.ernestasUrbonas.model.Stock;

public class StockMapper {
    public Stock mapDTOToEntity(StockDTO stockDTO) {
        Stock stock = new Stock();
        stock.setProduct(stockDTO.getProduct());
        stock.setQuantity(stockDTO.getQuantity());
        return stock;
    }

    public StockDTO mapEntityToDTO(Stock stock) {
        return new StockDTO(
                stock.getId(),
                stock.getProduct(),
                stock.getWarehouse(),
                stock.getQuantity()
        );
    }
}
