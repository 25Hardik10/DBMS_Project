package com.realestate.real_estate_management.repository;

import com.realestate.real_estate_management.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor; // <-- Add this import

// Add JpaSpecificationExecutor<Property> here
public interface PropertyRepository extends JpaRepository<Property, Long>, JpaSpecificationExecutor<Property> {
    
}