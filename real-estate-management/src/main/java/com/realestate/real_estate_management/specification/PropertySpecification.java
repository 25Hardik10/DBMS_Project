package com.realestate.real_estate_management.specification;

import com.realestate.real_estate_management.entity.Amenity; // <-- Add this import
import com.realestate.real_estate_management.entity.Property;
import jakarta.persistence.criteria.Join; // <-- Add this import
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.List; // <-- Add this import

@Component
public class PropertySpecification {

    public static Specification<Property> hasCity(String city) {
        return (root, query, criteriaBuilder) -> {
            if (city == null || city.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("city"), city);
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

    // --- NEW METHOD ---
    /**
     * Creates a specification to find properties that have ALL specified amenities.
     * @param amenities A list of amenity names (e.g., ["Gym", "Pool"]).
     * @return A Specification for the query.
     */
    public static Specification<Property> hasAmenities(List<String> amenities) {
        return (root, query, criteriaBuilder) -> {
            // If the list is null or empty, do nothing
            if (amenities == null || amenities.isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            // We must use "distinct" to avoid duplicate properties
            query.distinct(true);

            // Join the Property entity with its "amenities" collection
            // This is joining on the 'amenities' field we just added to Property.java
            Join<Property, Amenity> amenityJoin = root.join("amenities");

            // We need to build a list of predicates, one for each amenity
            // This will find properties where an amenity name is IN our list
            Predicate amenitiesInList = amenityJoin.get("amenityName").in(amenities);

            // This part is the "magic":
            // 1. We group by the property
            // 2. We check that the COUNT of matching amenities
            // 3. Is EQUAL to the number of amenities we're searching for
            // This guarantees the property has *all* of them.
            query.groupBy(root.get("propertyId"));
            query.having(criteriaBuilder.equal(criteriaBuilder.count(amenityJoin), amenities.size()));
            
            // The final query combines the "IN" clause with the "HAVING" clause
            return amenitiesInList;
        };
    }
}