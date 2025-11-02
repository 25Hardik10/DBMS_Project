package com.realestate.real_estate_management.service;

import com.realestate.real_estate_management.entity.Image;
import com.realestate.real_estate_management.entity.Property;
import com.realestate.real_estate_management.repository.ImageRepository;
import com.realestate.real_estate_management.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    public Image saveImage(Long propertyId, Image image) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found with id: " + propertyId));
        
        image.setProperty(property);
        image.setUploadDate(LocalDate.now());

        return imageRepository.save(image);
    }
    
    public List<Image> getImagesByPropertyId(Long propertyId) {
        return imageRepository.findByProperty_PropertyIdOrderByImageOrderAsc(propertyId);
    }
}