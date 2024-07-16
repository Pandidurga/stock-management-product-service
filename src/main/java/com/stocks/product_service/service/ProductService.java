package com.stocks.product_service.service;

import com.stocks.product_service.model.Product;
import com.stocks.product_service.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for managing Product entities.
 */
@Service
public class ProductService {

    @Autowired
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Saves a new product.
     *
     * @param product The product to be saved.
     * @return The saved product entity.
     */
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param productId The ID of the product to retrieve.
     * @return An Optional containing the product if found, otherwise empty.
     */
    public Optional<Product> getProductById(Integer productId) {
        return productRepository.findById(productId);
    }

    /**
     * Updates an existing product.
     *
     * @param productId     The ID of the product to update.
     * @param updatedProduct The updated product object.
     * @return An Optional containing the updated product if found, otherwise empty.
     */
    public Optional<Product> updateProduct(Integer productId, Product updatedProduct) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            updatedProduct.setProductId(productId);
            return Optional.of(productRepository.save(updatedProduct));
        } else {
            return Optional.empty();
        }
    }

    /**
     * Deletes a product by its ID.
     *
     * @param productId The ID of the product to delete.
     * @return True if the product was deleted, false if the product did not exist.
     */
    public boolean deleteProductById(Integer productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            productRepository.deleteById(productId);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Retrieves all products.
     *
     * @return A list of all products.
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    /**
     * Checks if a product with the given name exists in the database.
     *
     * @param productName The name of the product to check.
     * @return True if a product with the given name exists, otherwise false.
     */
    public boolean doesProductExist(String productName) {
        Optional<Product> existingProduct = productRepository.findByName(productName);
        return existingProduct.isPresent();
    }
    
    /**
     * Check if a product with the given name exists, excluding the product with the specified ID.
     *
     * @param productName The name of the product to check.
     * @param productId   The ID of the product to exclude from the check.
     * @return true if a product with the given name exists (excluding the specified ID), false otherwise.
     */
    public boolean doesProductExistExcludeId(String productName, Integer productId) {
        return productRepository.existsByNameAndIdNotCustomQuery(productName, productId);
    }
    
    
    /**
     * Searches products based on the given criteria.
     * 
     * @param keyword  Keyword to search in the product name.
     * @param minPrice Minimum selling price for filtering.
     * @param maxPrice Maximum selling price for filtering.
     * @return List of products matching the search criteria.
     */
    public List<Product> searchProducts(String keyword, BigDecimal minPrice, BigDecimal maxPrice) {
        return productRepository.searchProducts(keyword, minPrice, maxPrice);
    }

}
