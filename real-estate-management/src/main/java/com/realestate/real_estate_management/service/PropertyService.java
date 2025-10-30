package com.realestate.real_estate_management.service;

import com.realestate.real_estate_management.entity.Property;
import com.realestate.real_estate_management.repository.PropertyRepository;
import com.realestate.real_estate_management.specification.PropertySpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList; // <-- Add this import
import java.util.List; 
import java.util.Optional;

@Service
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    // We no longer need to autowire PropertySpecification
    // since we are calling its static methods.

    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    /**
     * Searches for properties based on dynamic criteria.
     * @param city Optional city to filter by.
     * @param propertyType Optional property type (e.g., "Flat", "Land") to filter by.
     * @return A list of matching properties.
     */
    public List<Property> searchProperties(String city, String propertyType) {
        
        // 1. Create a list to hold our "LEGO blocks"
        List<Specification<Property>> specs = new ArrayList<>();

        // 2. Add specifications to the list only if the parameter is present
        if (city != null && !city.isEmpty()) {
            specs.add(PropertySpecification.hasCity(city));
        }

        if (propertyType != null && !propertyType.isEmpty()) {
            specs.add(PropertySpecification.hasType(propertyType));
        }

        // 3. Combine all specifications in the list with an "AND" operator
        // Specification.allOf() is the modern replacement for where(null).and()...
        return propertyRepository.findAll(Specification.allOf(specs));
    }
    // ------------------------------------------

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