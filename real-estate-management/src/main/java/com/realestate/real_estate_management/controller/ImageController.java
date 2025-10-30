package com.realestate.real_estate_management.controller;

import com.realestate.real_estate_management.entity.Image;
import com.realestate.real_estate_management.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/properties") // Note: base URL is /api/properties
public class ImageController {

    @Autowired
    private ImageService imageService;

    /**
     * POST /api/properties/{propertyId}/images
     * Allows uploading a new image URL for a property.
     * @param propertyId The ID of the property.
     * @param image The JSON body with URL and Caption.
     * @return The created Image object.
     */
    @PostMapping("/{propertyId}/images")
    public ResponseEntity<Image> uploadImage(
            @PathVariable Long propertyId,
            @RequestBody Image image) {
        
        // Note: This endpoint should be protected and only accessible by the property owner (Seller)
        // For now, any authenticated user can post, but it requires authentication.
        Image savedImage = imageService.saveImage(propertyId, image);
        
        return ResponseEntity.ok(savedImage);
    }
    
    /**
     * GET /api/properties/{propertyId}/images
     * Retrieves all images for a specific property.
     * @param propertyId The ID of the property.
     * @return A list of Image objects.
     */
    @GetMapping("/{propertyId}/images")
    public List<Image> getImagesByProperty(@PathVariable Long propertyId) {
        return imageService.getImagesByPropertyId(propertyId);
    }
}