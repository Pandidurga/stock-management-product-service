package com.stocks.product_service.service;

import com.stocks.product_service.model.Supplier;
import com.stocks.product_service.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for managing Supplier entities.
 * Handles business logic for CRUD operations on suppliers.
 */
@Service
public class SupplierService {

	@Autowired
	private final SupplierRepository supplierRepository;

    
    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    /**
     * Saves a new supplier.
     *
     * @param supplier The supplier object to be saved.
     * @return The saved supplier entity.
     */
    public Supplier saveSupplier(Supplier supplier) {
        if (supplier == null) {
            throw new IllegalArgumentException("Supplier object cannot be null.");
        }

        // Example: Perform additional validation or business logic before saving

        return supplierRepository.save(supplier);
    }

    /**
     * Retrieves a supplier by ID.
     *
     * @param id The ID of the supplier to retrieve.
     * @return Optional containing the supplier entity if found, otherwise empty.
     */
    public Optional<Supplier> getSupplierById(Integer id) {
        return supplierRepository.findById(id);
    }

    /**
     * Updates an existing supplier.
     *
     * @param supplierId     The ID of the supplier to update.
     * @param updatedSupplier The updated supplier object.
     * @return An Optional containing the updated supplier if found, otherwise empty.
     */
    public Optional<Supplier> updateSupplier(Integer supplierId, Supplier updatedSupplier) {
        Optional<Supplier> supplierOptional = supplierRepository.findById(supplierId);
        if (supplierOptional.isPresent()) {
            updatedSupplier.setSupplierId(supplierId);
            return Optional.of(supplierRepository.save(updatedSupplier));
        } else {
            return Optional.empty();
        }
    }

    /**
     * Deletes a supplier by ID.
     *
     * @param id The ID of the supplier to delete.
     * @return True if the supplier was deleted, false if no supplier with the given ID exists.
     */
    public boolean deleteSupplierById(Integer id) {
        Optional<Supplier> supplierOptional = supplierRepository.findById(id);
        if (supplierOptional.isPresent()) {
            supplierRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Retrieves all suppliers.
     *
     * @return A list of all supplier entities.
     */
    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }
    
    /**
     * Checks if a supplier with the given name already exists in the database.
     *
     * @param supplierName The name of the supplier to check.
     * @return True if a supplier with the given name exists, otherwise false.
     */
    public boolean doesSupplierExist(String supplierName) {
        Optional<Supplier> existingSupplier = supplierRepository.findBySupplierName(supplierName);
        return existingSupplier.isPresent();
    }

    /**
     * Checks if a supplier with the given name exists, excluding the supplier with the specified ID.
     *
     * @param supplierName The name of the supplier to check.
     * @param supplierId   The ID of the supplier to exclude from the check.
     * @return true if a supplier with the given name exists (excluding the specified ID), false otherwise.
     */
    public boolean doesSupplierExistNotId(String supplierName, Integer supplierId) {
        return supplierRepository.existsBySupplierNameAndIdNot(supplierName, supplierId);
    }
    
   }
