package com.gameStore.ernestasUrbonas.service;

import com.gameStore.ernestasUrbonas.dto.StockDTO;
import com.gameStore.ernestasUrbonas.mapper.StockMapper;
import com.gameStore.ernestasUrbonas.model.Product;
import com.gameStore.ernestasUrbonas.model.Stock;
import com.gameStore.ernestasUrbonas.model.Warehouse;
import com.gameStore.ernestasUrbonas.repository.ProductRepository;
import com.gameStore.ernestasUrbonas.repository.StockRepository;
import com.gameStore.ernestasUrbonas.repository.WarehouseRepository;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    private final StockRepository stockRepository;
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;

    private final StockMapper stockMapper;

    @Autowired
    public StockService(StockRepository stockRepository, ProductRepository productRepository, WarehouseRepository warehouseRepository, StockMapper stockMapper) {
        this.stockRepository = stockRepository;
        this.productRepository = productRepository;
        this.warehouseRepository = warehouseRepository;
        this.stockMapper = stockMapper;
    }

    /**
     * Create a new stock.
     *
     * @param stockDTO Data Transfer Object containing stock details.
     * @return The created StockDTO.
     */

    public StockDTO createStock(StockDTO stockDTO) {

        Product product = productRepository.findById(stockDTO.getIdProduct())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        Warehouse warehouse = warehouseRepository.findById(stockDTO.getIdWarehouse())
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found"));

        Stock stock = this.stockMapper.mapDTOToEntity(stockDTO, product, warehouse);
        Stock savedStock = this.stockRepository.save(stock);
        return this.stockMapper.mapEntityToDTO(savedStock);
    }
}
