package com.realestate.real_estate_management.service;

import com.realestate.real_estate_management.entity.Property;
import com.realestate.real_estate_management.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List; 
import java.util.Optional;

@Service
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    public Property saveProperty(Property property) {
        return propertyRepository.save(property);
    }

    public Optional<Property> getPropertyById(Long id) {
        return propertyRepository.findById(id);
    }
    public void deletePropertyById(Long id) {
        propertyRepository.deleteById(id);
    }
    public Optional<Property> updateProperty(Long id, Property propertyDetails) {
        return propertyRepository.findById(id)
            .map(existingProperty -> {
                existingProperty.setPrice(propertyDetails.getPrice());
                existingProperty.setPropertyStatus(propertyDetails.getPropertyStatus());
                existingProperty.setDescription(propertyDetails.getDescription());
                return propertyRepository.save(existingProperty);
            });
    }
}