package com.realestate.real_estate_management.repository;

import com.realestate.real_estate_management.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List; // <-- Add this import

public interface PropertyRepository extends JpaRepository<Property, Long>, JpaSpecificationExecutor<Property> {
    
    // --- NEW METHOD ---
    /**
     * Finds all properties owned by a seller, identified by their email.
     * @param email The seller's email.
     * @return A list of their properties.
     */
    List<Property> findBySeller_Email(String email);
    // --- END NEW METHOD ---
}