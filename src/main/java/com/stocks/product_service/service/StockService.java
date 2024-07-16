//StockService.java
package com.stocks.product_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stocks.product_service.model.Stock;
import com.stocks.product_service.repository.StockRepository;
import java.util.List;
import java.util.Optional;

/**
* Service class for managing stock operations.
* This class provides methods to perform CRUD operations on the Stock table.
*/

@Service
public class StockService {

 @Autowired
 private StockRepository stockRepository;

 /**
  * Retrieves all stocks.
  *
  * @return a list of all Stock entities.
  */
 public List<Stock> getAllStocks() {
     return stockRepository.findAll();
 }

 /**
  * Retrieves a stock by product ID.
  *
  * @param productId the ID of the product to retrieve the stock for.
  * @return an Optional containing the Stock entity if found, otherwise empty.
  */
 public Optional<Stock> getStockById(Integer productId) {
     return stockRepository.findById(productId);
 }

 /**
  * Saves a new stock.
  *
  * @param stock the Stock entity to save.
  * @return the saved Stock entity.
  */
 public Stock saveStock(Stock stock) {
     return stockRepository.save(stock);
 }

 /**
  * Updates an existing stock.
  *
  * @param stock the Stock entity to update.
  * @return the updated Stock entity.
  */
 public Stock updateStock(Stock stock) {
     return stockRepository.save(stock);
 }

 /**
  * Deletes a stock by product ID.
  *
  * @param productId the ID of the product to delete the stock for.
  * @return true if the stock was deleted successfully, false otherwise.
  */
 public boolean deleteStock(Integer productId) {
     if (stockRepository.existsById(productId)) {
         stockRepository.deleteById(productId);
         return true;
     }
     return false;
 }
}
