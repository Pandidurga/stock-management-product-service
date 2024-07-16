package com.stocks.product_service.model;

import java.math.BigDecimal;

import jakarta.persistence.*;

/**
 * Represents a Product entity that stores product information.
 * This entity is mapped to the 'Products' table in the database.
 */
@Entity
@Table(name = "Products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId; // Unique identifier for the product entity

    @Column(name = "name", unique=true)
    private String name; // Name of the product

    @Column(name = "cost_price", precision = 10, scale = 2)
    private BigDecimal costPrice; // Cost price of the product using BigDecimal for precision

    @Column(name = "selling_price", precision = 10, scale = 2)
    private BigDecimal sellingPrice; // Selling price of the product using BigDecimal for precision

    @Column(name = "cgst", precision = 5, scale = 2)
    private BigDecimal cgst; // CGST percentage for the product

    @Column(name = "sgst", precision = 5, scale = 2)
    private BigDecimal sgst; // SGST percentage for the product

    @Column(name = "igst", precision = 5, scale = 2)
    private BigDecimal igst; // IGST percentage for the product

    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier; // Supplier of the product, linked via supplier_id in the database

    // Constructors, getters, setters, and other methods can be added here.

    /**
     * Getter for productId.
     * @return The productId.
     */
    public Integer getProductId() {
        return productId;
    }

    /**
     * Setter for productId.
     * @param productId The productId to set.
     */
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    /**
     * Getter for name.
     * @return The name.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for name.
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for costPrice.
     * @return The costPrice.
     */
    public BigDecimal getCostPrice() {
        return costPrice;
    }

    /**
     * Setter for costPrice.
     * @param costPrice The costPrice to set.
     */
    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    /**
     * Getter for sellingPrice.
     * @return The sellingPrice.
     */
    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    /**
     * Setter for sellingPrice.
     * @param sellingPrice The sellingPrice to set.
     */
    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    /**
     * Getter for cgst.
     * @return The cgst.
     */
    public BigDecimal getCgst() {
        return cgst;
    }

    /**
     * Setter for cgst.
     * @param cgst The cgst to set.
     */
    public void setCgst(BigDecimal cgst) {
        this.cgst = cgst;
    }

    /**
     * Getter for sgst.
     * @return The sgst.
     */
    public BigDecimal getSgst() {
        return sgst;
    }

    /**
     * Setter for sgst.
     * @param sgst The sgst to set.
     */
    public void setSgst(BigDecimal sgst) {
        this.sgst = sgst;
    }

    /**
     * Getter for igst.
     * @return The igst.
     */
    public BigDecimal getIgst() {
        return igst;
    }

    /**
     * Setter for igst.
     * @param igst The igst to set.
     */
    public void setIgst(BigDecimal igst) {
        this.igst = igst;
    }

    /**
     * Getter for supplier.
     * @return The supplier.
     */
    public Supplier getSupplier() {
        return supplier;
    }

    /**
     * Setter for supplier.
     * @param supplier The supplier to set.
     */
    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}
