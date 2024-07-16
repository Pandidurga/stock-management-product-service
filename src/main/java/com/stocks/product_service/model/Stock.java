//Stock.java
package com.stocks.product_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
* The Stock class represents the stock information for a product in the inventory.
* This class maps to the "Stock" table in the database.
*/
@Entity
@Table(name = "Stock")
public class Stock {

 /**
  * The product ID, which is the primary key of the Stock table.
  */
 @Id
 @Column(name = "product_id")
 private Integer productId;

 /**
  * The available quantity of the product in stock.
  */
 @Column(name = "available_quantity")
 private Integer availableQuantity;

 /**
  * The threshold quantity of the product in stock, used to determine when to reorder.
  */
 @Column(name = "threshold_quantity")
 private Integer thresholdQuantity;

 /**
  * Default constructor.
  */
 public Stock() {}

 /**
  * Parameterized constructor to initialize the Stock object.
  * 
  * @param productId The ID of the product.
  * @param availableQuantity The available quantity of the product in stock.
  * @param thresholdQuantity The threshold quantity of the product in stock.
  */
 public Stock(Integer productId, Integer availableQuantity, Integer thresholdQuantity) {
     this.productId = productId;
     this.availableQuantity = availableQuantity;
     this.thresholdQuantity = thresholdQuantity;
 }

 /**
  * Gets the product ID.
  * 
  * @return The product ID.
  */
 public Integer getProductId() {
     return productId;
 }

 /**
  * Sets the product ID.
  * 
  * @param productId The product ID.
  */
 public void setProductId(Integer productId) {
     this.productId = productId;
 }

 /**
  * Gets the available quantity of the product in stock.
  * 
  * @return The available quantity.
  */
 public Integer getAvailableQuantity() {
     return availableQuantity;
 }

 /**
  * Sets the available quantity of the product in stock.
  * 
  * @param availableQuantity The available quantity.
  */
 public void setAvailableQuantity(Integer availableQuantity) {
     this.availableQuantity = availableQuantity;
 }

 /**
  * Gets the threshold quantity of the product in stock.
  * 
  * @return The threshold quantity.
  */
 public Integer getThresholdQuantity() {
     return thresholdQuantity;
 }

 /**
  * Sets the threshold quantity of the product in stock.
  * 
  * @param thresholdQuantity The threshold quantity.
  */
 public void setThresholdQuantity(Integer thresholdQuantity) {
     this.thresholdQuantity = thresholdQuantity;
 }
}
