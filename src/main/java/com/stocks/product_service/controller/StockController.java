// StockController.java
package com.stocks.product_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.stocks.product_service.model.Stock;
import com.stocks.product_service.service.StockService;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing stocks.
 * This controller provides endpoints to perform CRUD operations on the Stock entity.
 */
@RestController
@RequestMapping("/api/product-service/stocks")
public class StockController {

    @Autowired
    private StockService stockService;

    /**
     * Get all stocks.
     *
     * @return a list of all Stock entities.
     */
    @GetMapping("/get-all")
    public ResponseEntity<Object> getAllStocks() {
        try {
            List<Stock> stocks = stockService.getAllStocks();
            return ResponseEntity.ok(stocks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve stocks: " + e.getMessage());
        }
    }

    /**
     * Retrieves a stock by product ID.
     *
     * @param productId the ID of the product to retrieve the stock for.
     * @return ResponseEntity with the Stock entity if found, or a 404 Not Found response if not found.
     */
    @GetMapping("/get-by-id/{productId}")
    public ResponseEntity<Object> getStockById(@PathVariable Integer productId) {
        try {
            Optional<Stock> stock = stockService.getStockById(productId);
            if (stock.isPresent()) {
                return ResponseEntity.ok(stock.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                     .body("Stock with product ID " + productId + " not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Failed to retrieve stock: " + e.getMessage());
        }
    }


    /**
     * Create a new stock.
     *
     * @param stock the Stock entity to create.
     * @return the created Stock entity.
     */
    @PostMapping("/add")
    public ResponseEntity<Object> createStock(@RequestBody Stock stock) {
        try {
            // Check if the stock already exists
            if (stockService.getStockById(stock.getProductId()).isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Stock with product ID '" + stock.getProductId() + "' already exists.");
            }
            Stock createdStock = stockService.saveStock(stock);
            return ResponseEntity.status(HttpStatus.CREATED).body("Stock created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create stock: " + e.getMessage());
        }
    }

    /**
     * Update an existing stock.
     *
     * @param productId the ID of the product to update the stock for.
     * @param stockDetails the details of the Stock entity to update.
     * @return the updated Stock entity, or a 404 Not Found response if the stock does not exist.
     */
    @PutMapping("/update/{productId}")
    public ResponseEntity<Object> updateStock(@PathVariable Integer productId, @RequestBody Stock stockDetails) {
        try {
            Optional<Stock> stockOptional = stockService.getStockById(productId);
            if (stockOptional.isPresent()) {
                Stock stock = stockOptional.get();
                stock.setAvailableQuantity(stockDetails.getAvailableQuantity());
                stock.setThresholdQuantity(stockDetails.getThresholdQuantity());
                stockService.updateStock(stock);
                return ResponseEntity.status(HttpStatus.OK).body("Stock with product ID " + productId + " updated successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Stock with product ID " + productId + " not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update stock: " + e.getMessage());
        }
    }

    /**
     * Delete a stock by product ID.
     *
     * @param productId the ID of the product to delete the stock for.
     * @return a 204 No Content response if the stock was deleted, or a 404 Not Found response if the stock does not exist.
     */
    @DeleteMapping("delete/{productId}")
    public ResponseEntity<Object> deleteStock(@PathVariable Integer productId) {
        try {
            boolean deleted = stockService.deleteStock(productId);
            if (deleted) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Stock with product ID " + productId + " deleted successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Stock with product ID " + productId + " not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete stock: " + e.getMessage());
        }
    }
}
