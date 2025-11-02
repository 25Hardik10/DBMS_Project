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
@RequestMapping("/api/properties") 
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping("/{propertyId}/images")
    public ResponseEntity<Image> uploadImage(
            @PathVariable Long propertyId,
            @RequestBody Image image) {
        
        Image savedImage = imageService.saveImage(propertyId, image);
        
        return ResponseEntity.ok(savedImage);
    }
    
    @GetMapping("/{propertyId}/images")
    public List<Image> getImagesByProperty(@PathVariable Long propertyId) {
        return imageService.getImagesByPropertyId(propertyId);
    }
}