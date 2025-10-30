package com.realestate.real_estate_management.service;

import com.realestate.real_estate_management.entity.Image;
import com.realestate.real_estate_management.entity.Property;
import com.realestate.real_estate_management.repository.ImageRepository;
import com.realestate.real_estate_management.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    /**
     * Creates and saves a new image for a property.
     * @param propertyId The ID of the property to link the image to.
     * @param image The Image entity from the request body.
     * @return The saved Image entity.
     */
    public Image saveImage(Long propertyId, Image image) {
        // 1. Fetch the Property to link it
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found with id: " + propertyId));
        
        // 2. Set the relationship fields and the required UploadDate
        image.setProperty(property);
        image.setUploadDate(LocalDate.now());

        // 3. Save the image
        return imageRepository.save(image);
    }
    
    /**
     * Retrieves all images for a given property ID.
     * @param propertyId The ID of the property.
     * @return A list of images for that property.
     */
    public List<Image> getImagesByPropertyId(Long propertyId) {
        return imageRepository.findByProperty_PropertyId(propertyId);
    }
}