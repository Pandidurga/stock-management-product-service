package com.stocks.product_service.repository;

import com.stocks.product_service.model.Supplier;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Supplier entities.
 * Extends JpaRepository which provides basic CRUD operations.
 */
@Repository // Indicates that this interface is a Spring repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {

	
	Optional<Supplier> findBySupplierName(String supplierName);
	
	@Query("SELECT COUNT(s) > 0 FROM Supplier s WHERE s.supplierName = :supplierName AND s.id <> :supplierId")
    boolean existsBySupplierNameAndIdNot(@Param("supplierName") String supplierName, @Param("supplierId") Integer supplierId);

}
