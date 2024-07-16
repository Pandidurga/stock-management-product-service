package com.stocks.product_service.repository;

import com.stocks.product_service.model.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Product entities.
 * Extends JpaRepository which provides basic CRUD operations.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

	
	/**
	 * This method retrieves an Optional containing a Product entity based on the provided name.
	 * If a product with the given name exists, it will be returned inside the Optional; 
	 * otherwise, the Optional will be empty.
	 * 
	 * @param name the name of the product to find
	 * @return an Optional containing the Product entity if found, otherwise empty
	 */
	Optional<Product> findByName(String name);

	/**
	 * This method checks if a product with the given name exists, excluding the product with the specified id.
	 * It uses a custom query to count the number of products with the given name, where the id is not equal 
	 * to the provided productId. The method returns true if such a product exists, otherwise false.
	 * 
	 * @param productName the name of the product to check for existence
	 * @param productId the id of the product to exclude from the check
	 * @return true if a product with the given name exists and has a different id, otherwise false
	 */
	@Query("SELECT COUNT(p) > 0 FROM Product p WHERE p.name = :productName AND p.id <> :productId")
	boolean existsByNameAndIdNotCustomQuery(@Param("productName") String productName, @Param("productId") Integer productId);

    
	/**
     * Custom query to search products by keyword, minimum price, and maximum price.
     * This query is case-insensitive and allows optional filtering by each parameter.
     * 
     * @param keyword  Keyword to search in the product name (case-insensitive).
     * @param minPrice Minimum selling price for filtering.
     * @param maxPrice Maximum selling price for filtering.
     * @return List of products matching the search criteria.
     */
    @Query("SELECT p FROM Product p WHERE " +
           "(:keyword IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
           "AND (:minPrice IS NULL OR p.sellingPrice >= :minPrice) " +
           "AND (:maxPrice IS NULL OR p.sellingPrice <= :maxPrice)")
    List<Product> searchProducts(@Param("keyword") String keyword,
                                 @Param("minPrice") BigDecimal minPrice,
                                 @Param("maxPrice") BigDecimal maxPrice);

}
