package com.realestate.real_estate_management.repository;

import com.realestate.real_estate_management.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    
    // Custom method to fetch all images associated with a specific property ID
    List<Image> findByProperty_PropertyId(Long propertyId);
}