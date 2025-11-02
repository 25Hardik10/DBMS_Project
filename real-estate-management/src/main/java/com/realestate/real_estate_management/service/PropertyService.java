package com.realestate.real_estate_management.service;

import com.realestate.real_estate_management.entity.Property;
import com.realestate.real_estate_management.entity.Seller;
import com.realestate.real_estate_management.repository.BoughtRepository;
import com.realestate.real_estate_management.repository.ImageRepository;
import com.realestate.real_estate_management.repository.LeaseRepository;
import com.realestate.real_estate_management.repository.PropertyRepository;
import com.realestate.real_estate_management.repository.ReviewRepository;
import com.realestate.real_estate_management.specification.PropertySpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Objects;

@Service
@Transactional
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired private ImageRepository imageRepository;
    @Autowired private ReviewRepository reviewRepository;
    @Autowired private BoughtRepository boughtRepository;
    @Autowired private LeaseRepository leaseRepository;

    public void deleteMyProperty(Long propertyId, String sellerEmail) {
        // 1. Find the property
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new ResourceAccessException("Property not found with id: " + propertyId));

        // 2. Check Ownership
        if (!Objects.equals(property.getSeller().getEmail(), sellerEmail)) {
            throw new AccessDeniedException("You do not have permission to delete this property.");
        }

        // 3. Delete it
        deletePropertyAndDependencies(property);
    }
    
    public void deletePropertyAndDependencies(Property property) {
        // 1. Delete all child records that reference this property
        // Note: Amenities are deleted by CascadeType.ALL on the Property entity
        imageRepository.deleteAllByProperty(property);
        reviewRepository.deleteAllByProperty(property);
        boughtRepository.deleteAllByProperty(property);
        leaseRepository.deleteAllByProperty(property);
        
        // 2. Delete the property itself (and its subclass entry)
        propertyRepository.delete(property);
    }
    
    // --- FIX: NEW METHOD FOR DELETING ALL OF A SELLER'S PROPERTIES ---
    public void deleteAllPropertiesBySeller(Seller seller) {
        List<Property> properties = propertyRepository.findBySeller_Email(seller.getEmail());
        for (Property property : properties) {
            deletePropertyAndDependencies(property);
        }
    }

    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }
    
    public List<Property> searchProperties(String city, 
                                           String state,
                                           String propertyType, 
                                           BigDecimal minPrice, 
                                           BigDecimal maxPrice,
                                           List<String> amenities) { // <-- Add this parameter
        
        List<Specification<Property>> specs = new ArrayList<>();

        if (propertyType != null && !propertyType.isEmpty()) {
            specs.add(PropertySpecification.hasType(propertyType));
        }

        if (city != null && !city.isEmpty()) {
            specs.add(PropertySpecification.hasCity(city));
        }

        if (state != null && !state.isEmpty()) {
            specs.add(PropertySpecification.hasState(state)); // Use the new spec
        }
        
        if (minPrice != null || maxPrice != null) {
            specs.add(PropertySpecification.hasPriceBetween(minPrice, maxPrice));
        }

        if (amenities != null && !amenities.isEmpty()) {
            specs.add(PropertySpecification.hasAmenities(amenities));
        }

        return propertyRepository.findAll(Specification.allOf(specs));
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

    public List<Property> getPropertiesBySellerEmail(String email) {
        return propertyRepository.findBySeller_Email(email);
    }
}