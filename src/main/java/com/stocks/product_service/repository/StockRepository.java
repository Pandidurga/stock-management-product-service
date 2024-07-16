//StockRepository.java
package com.stocks.product_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.stocks.product_service.model.Stock;

/**
* Repository interface for Stock entity.
* This interface extends JpaRepository and provides methods to perform CRUD operations on the Stock table.
*/
@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {
}
