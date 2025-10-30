package com.realestate.real_estate_management.service;

import com.realestate.real_estate_management.entity.Property;
import com.realestate.real_estate_management.repository.PropertyRepository;
import com.realestate.real_estate_management.specification.PropertySpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    /**
     * Searches for properties based on dynamic criteria.
     * @param city Optional city to filter by.
     ** @param propertyType Optional property type (e.g., "Flat", "Land") to filter by.
     * @param minPrice Optional minimum price to filter by.
     * @param maxPrice Optional maximum price to filter by.
     * @param amenities Optional list of amenities the property must have.
     * @return A list of matching properties.
     */
    public List<Property> searchProperties(String city, 
                                           String propertyType, 
                                           BigDecimal minPrice, 
                                           BigDecimal maxPrice,
                                           List<String> amenities) { // <-- Add this parameter
        
        List<Specification<Property>> specs = new ArrayList<>();

        if (city != null && !city.isEmpty()) {
            specs.add(PropertySpecification.hasCity(city));
        }

        if (propertyType != null && !propertyType.isEmpty()) {
            specs.add(PropertySpecification.hasType(propertyType));
        }
        
        if (minPrice != null || maxPrice != null) {
            specs.add(PropertySpecification.hasPriceBetween(minPrice, maxPrice));
        }

        // --- NEW BLOCK ---
        // Add the amenities filter
        if (amenities != null && !amenities.isEmpty()) {
            specs.add(PropertySpecification.hasAmenities(amenities));
        }
        // --- END NEW BLOCK ---

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

    public List<Property> getPropertiesBySellerEmail(String email) {
        return propertyRepository.findBySeller_Email(email);
    }
}