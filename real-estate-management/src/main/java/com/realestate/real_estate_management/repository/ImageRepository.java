package com.realestate.real_estate_management.repository;

import com.realestate.real_estate_management.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    
    // --- UPDATED METHOD ---
    /**
     * Custom method to fetch all images associated with a specific property ID,
     * automatically sorted by the 'imageOrder' field in ascending order.
     * @param propertyId The ID of the property.
     * @return A list of images, sorted by imageOrder.
     */
    List<Image> findByProperty_PropertyIdOrderByImageOrderAsc(Long propertyId);
    // --- END UPDATED METHOD ---
}