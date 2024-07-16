package com.stocks.product_service.controller;

import com.stocks.product_service.model.Product;
import com.stocks.product_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Controller class for handling HTTP requests related to Product entities.
 * Provides endpoints for CRUD operations on products.
 */
@RestController
@RequestMapping("/api/product-service/products")
public class ProductController {

    @Autowired
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Endpoint to create a new product.
     *
     * @param product The product object to be created.
     * @return ResponseEntity with the created product and HTTP status 201 (Created) if successful,
     *         or HTTP status 500 (Internal Server Error) with an error message if creation fails.
     */

    /**
     * Endpoint to create a new product.
     * 
     * @param product The product object to be created.
     * @return ResponseEntity with the created product and HTTP status 201 (Created) if successful,
     *         or HTTP status 409 (Conflict) if a product with the same name already exists,
     *         or HTTP status 500 (Internal Server Error) with an error message if creation fails.
     */
    @PostMapping("/add")
    public ResponseEntity<Object> createProduct(@RequestBody Product product) {
        try {
            // Check if the product already exists
            if (productService.doesProductExist(product.getName())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Product '" + product.getName() + "' already exists.");
            }

            // If product does not exist, save it
            Product savedProduct = productService.saveProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED).body("Product Created Successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create product: " + e.getMessage());
        }
    }

    /**
     * Endpoint to retrieve a product by ID.
     *
     * @param id The ID of the product to retrieve.
     * @return ResponseEntity with the retrieved product and HTTP status 200 (OK) if found,
     *         or HTTP status 404 (Not Found) if no product with the given ID exists,
     *         or HTTP status 500 (Internal Server Error) with an error message if retrieval fails.
     */
    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable Integer id) {
        try {
            Optional<Product> productOptional = productService.getProductById(id);
            if (productOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(productOptional.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product with ID " + id + " not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch product: " + e.getMessage());
        }
    }

    
    /**
     * Updates an existing product.
     *
     * @param id             The ID of the product to update.
     * @param updatedProduct The updated product object.
     * @return ResponseEntity with success or error message.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable Integer id, @RequestBody Product updatedProduct) {
        try {
            // Check if the updated name already exists (excluding the current product)
            boolean productExistsWithSameName = productService.doesProductExistExcludeId(updatedProduct.getName(), id);
            
            // Check if the product ID exists
            if (!productService.getProductById(id).isPresent()) {
                // Product with given ID does not exist
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product with ID " + id + " not found.");
            } else if (productExistsWithSameName) {
                // Product with the updated name already exists
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Product with name '" + updatedProduct.getName() + "' already exists.");
            } else {
                // Update the product
                Optional<Product> updatedProductOptional = productService.updateProduct(id, updatedProduct);
                if (updatedProductOptional.isPresent()) {
                    return ResponseEntity.status(HttpStatus.OK)
                            .body("Product with ID " + id + " updated successfully");
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Failed to update product with ID " + id);
                }
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update product: " + e.getMessage());
        }
    }

    
    /**
     * Endpoint to delete a product by ID.
     *
     * @param id The ID of the product to delete.
     * @return ResponseEntity with HTTP status 204 (No Content) if deletion is successful,
     *         or HTTP status 404 (Not Found) if no product with the given ID exists,
     *         or HTTP status 500 (Internal Server Error) with an error message if deletion fails.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable Integer id) {
        try {
            boolean deleted = productService.deleteProductById(id);
            if (deleted) {
            	return ResponseEntity.status(HttpStatus.OK).body("Product with ID " + id + " deleted successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product with ID " + id + " not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete product: " + e.getMessage());
        }
    }

    /**
     * Endpoint to retrieve all products.
     *
     * @return ResponseEntity with the list of products and HTTP status 200 (OK) if successful,
     *         or HTTP status 404 (Not Found) if no products are found,
     *         or HTTP status 500 (Internal Server Error) with an error message if retrieval fails.
     */
    @GetMapping("/get-all")
    public ResponseEntity<Object> getAllProducts() {
        try {
            List<Product> products = productService.getAllProducts();
            if (products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No products found.");
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(products);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch products: " + e.getMessage());
        }
    }
    
    /**
     * Endpoint to search products based on keyword and price range.
     * 
     * @param keyword  Keyword to search in the product name (optional).
     * @param minPrice Minimum selling price for filtering (optional).
     * @param maxPrice Maximum selling price for filtering (optional).
     * @return List of products matching the search criteria.
     */
    @GetMapping("/search")
    public List<Product> searchProducts(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "minPrice", required = false) BigDecimal minPrice,
            @RequestParam(value = "maxPrice", required = false) BigDecimal maxPrice) {
        return productService.searchProducts(keyword, minPrice, maxPrice);
    }
}
