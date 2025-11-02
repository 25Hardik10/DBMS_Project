package com.realestate.real_estate_management.specification;

import com.realestate.real_estate_management.entity.Amenity; 
import com.realestate.real_estate_management.entity.Property;
import jakarta.persistence.criteria.Join; 
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.List; 

@Component
public class PropertySpecification {

    public static Specification<Property> hasCity(String city) {
        return (root, query, criteriaBuilder) -> {
            if (city == null || city.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("city")), "%" + city.toLowerCase() + "%");
        };
    }

    public static Specification<Property> hasState(String state) {
        return (root, query, criteriaBuilder) -> {
            if (state == null || state.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            // Use like for partial matching
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("state")), "%" + state.toLowerCase() + "%");
        };
    }

    public static Specification<Property> hasType(String propertyType) {
        return (root, query, criteriaBuilder) -> {
            if (propertyType == null || propertyType.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("propertyType"), propertyType);
        };
    }

    public static Specification<Property> hasPriceBetween(BigDecimal minPrice, BigDecimal maxPrice) {
        return (root, query, criteriaBuilder) -> {
            if (minPrice == null && maxPrice == null) {
                return criteriaBuilder.conjunction();
            }
            if (minPrice != null && maxPrice != null) {
                return criteriaBuilder.between(root.get("price"), minPrice, maxPrice);
            }
            if (minPrice != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
        };
    }

    // --- FIX: This is now a "Match-Any" (OR) search ---
    public static Specification<Property> hasAmenities(List<String> amenities) {
        return (root, query, criteriaBuilder) -> {
            if (amenities == null || amenities.isEmpty()) {
                return criteriaBuilder.conjunction(); // Return all if no amenities are specified
            }

            // Use distinct to ensure we don't get duplicate properties
            // if a property has multiple matching amenities
            query.distinct(true); 

            // Join with the amenities table
            Join<Property, Amenity> amenityJoin = root.join("amenities");

            // Create an "IN" clause: ... WHERE amenity.amenityName IN ('Pool', 'Gym', ...)
            Predicate amenitiesInList = amenityJoin.get("amenityName").in(amenities);

            // We no longer need GROUP BY or HAVING
            
            return amenitiesInList;
        };
    }
    // --- END FIX ---
}