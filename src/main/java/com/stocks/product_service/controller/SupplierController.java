package com.stocks.product_service.controller;

import com.stocks.product_service.model.Supplier;
import com.stocks.product_service.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller for managing Supplier entities.
 */
@RestController
@RequestMapping("/api/product-service/suppliers")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    /**
     * Creates a new supplier.
     *
     * @param supplier The supplier object to be created.
     * @return ResponseEntity with the created supplier.
     */
    @PostMapping("/add")
    public ResponseEntity<Object> createSupplier(@RequestBody Supplier supplier) {
        try {
            // Check if the supplier name already exists
            if (supplierService.doesSupplierExist(supplier.getSupplierName())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Supplier with name '" + supplier.getSupplierName() + "' already exists.");
            }

            Supplier createdSupplier = supplierService.saveSupplier(supplier);
            return ResponseEntity.status(HttpStatus.CREATED).body("Supplier created Successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create supplier: " + e.getMessage());
        }
    }

    /**
     * Retrieves a supplier by ID.
     *
     * @param id The ID of the supplier to retrieve.
     * @return ResponseEntity with the supplier.
     */
    @GetMapping("get-by-id/{id}")
    public ResponseEntity<Object> getSupplierById(@PathVariable Integer id) {
        Optional<Supplier> supplierOptional = supplierService.getSupplierById(id);
        if (supplierOptional.isPresent()) {
            return ResponseEntity.ok(supplierOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Supplier with ID " + id + " not found.");
        }
    }

    /**
     * Updates an existing supplier.
     *
     * @param id The ID of the supplier to update.
     * @param updatedSupplier The updated supplier object.
     * @return ResponseEntity with the updated supplier.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateSupplier(@PathVariable Integer id, @RequestBody Supplier updatedSupplier) {
        try {
            // Check if the updated supplier name already exists (excluding the current supplier)
            if (supplierService.doesSupplierExistNotId(updatedSupplier.getSupplierName(), id)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Supplier with name '" + updatedSupplier.getSupplierName() + "' already exists.");
            }

            Optional<Supplier> updatedSupplierOptional = supplierService.updateSupplier(id, updatedSupplier);
            if (updatedSupplierOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body("Supplier with ID " + id + " updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Supplier with ID " + id + " not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update supplier: " + e.getMessage());
        }
    }

    /**
     * Deletes a supplier by ID.
     *
     * @param id The ID of the supplier to delete.
     * @return ResponseEntity with the result of the delete operation.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteSupplierById(@PathVariable Integer id) {
        boolean deleted = supplierService.deleteSupplierById(id);
        if (deleted) {
            return ResponseEntity.status(HttpStatus.OK).body("Supplier with ID " + id + " deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Supplier with ID " + id + " not found.");
        }
    }

    /**
     * Retrieves all suppliers.
     *
     * @return ResponseEntity with a list of all suppliers.
     */
    @GetMapping("/get-all")
    public ResponseEntity<List<Supplier>> getAllSuppliers() {
        List<Supplier> suppliers = supplierService.getAllSuppliers();
        return ResponseEntity.ok(suppliers);
    }
}
