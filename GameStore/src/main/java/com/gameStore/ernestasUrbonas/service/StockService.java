package com.gameStore.ernestasUrbonas.service;

import com.gameStore.ernestasUrbonas.dto.StockDTO;
import com.gameStore.ernestasUrbonas.exception.NotFoundException;
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

        ProductWarehousePair pair = getProductAndWarehouse(stockDTO.getIdProduct(), stockDTO.getIdWarehouse());
        Stock stock = this.stockMapper.mapDTOToEntity(stockDTO, pair.product(), pair.warehouse());
        Stock savedStock = this.stockRepository.save(stock);
        return this.stockMapper.mapEntityToDTO(savedStock);
    }

    /**
     * Find stock by warehouse ID and product ID.
     *
     * @param warehouseId ID of the warehouse.
     * @param productId   ID of the product.
     * @return The found StockDTO.
     * @throws ResourceNotFoundException if the stock item is not found.
     */
    public StockDTO findStockByWarehouseAndProduct(Long warehouseId, Long productId) {

        ProductWarehousePair pair = getProductAndWarehouse(productId, warehouseId);
        Stock stock = stockRepository.findByWarehouseIdAndProductId(warehouseId, productId)
                .orElseThrow(() -> new NotFoundException(
                        "Stock not found for product " + pair.product().getName() +
                                " in warehouse " + pair.warehouse().getName()
                ));
        return stockMapper.mapEntityToDTO(stock);
    }

    /**
     * Update the quantity of a stock item.
     *
     * @param productId   ID of the product.
     * @param warehouseId ID of the warehouse.
     * @param quantity    New quantity to set.
     * @return The updated StockDTO.
     * @throws ResourceNotFoundException if the stock item is not found.
     */
    public StockDTO updateStockQuantity(Long productId, Long warehouseId, Integer quantity) {

        ProductWarehousePair pair = getProductAndWarehouse(productId, warehouseId);
        Stock stock = stockRepository.findByWarehouseIdAndProductId(warehouseId, productId)
                .orElseThrow(() -> new NotFoundException(
                        "Stock not found for product " + pair.product().getName() +
                                " in warehouse " + pair.warehouse().getName()
                ));
        stock.setQuantity(quantity);
        Stock updatedStock = stockRepository.save(stock);
        return stockMapper.mapEntityToDTO(updatedStock);
    }

    /**
     * Helper method to retrieve Product and Warehouse entities by their IDs.
     *
     * @param productId   ID of the product.
     * @param warehouseId ID of the warehouse.
     * @return A ProductWarehousePair containing the Product and Warehouse.
     * @throws ResourceNotFoundException if either the product or warehouse is not found.
     */
    private ProductWarehousePair getProductAndWarehouse (Long productId, Long warehouseId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new NotFoundException("Warehouse not found"));

        return new ProductWarehousePair(product, warehouse);
    }

    /**
     * A simple record to hold a Product and Warehouse pair.
     */
    record ProductWarehousePair(Product product, Warehouse warehouse) { }
}


