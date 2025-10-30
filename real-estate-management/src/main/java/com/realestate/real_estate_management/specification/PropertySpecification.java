package com.realestate.real_estate_management.specification;

import com.realestate.real_estate_management.entity.Property;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class PropertySpecification {

    /**
     * Creates a specification to find properties by city.
     * @param city The city name to filter by.
     * @return A Specification for the query.
     */
    public static Specification<Property> hasCity(String city) {
        return (root, query, criteriaBuilder) -> {
            // If the city is null or empty, don't add this filter
            if (city == null || city.isEmpty()) {
                return criteriaBuilder.conjunction(); // A "true" predicate that does nothing
            }
            // This is the "WHERE city = '...' " part of the query
            return criteriaBuilder.equal(root.get("city"), city);
        };
    }

    /**
     * Creates a specification to find properties by type (e.g., "Flat", "Land").
     * @param propertyType The type to filter by.
     * @return A Specification for the query.
     */
    public static Specification<Property> hasType(String propertyType) {
        return (root, query, criteriaBuilder) -> {
            if (propertyType == null || propertyType.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            // This is the "WHERE PropType = '...' " part of the query
            return criteriaBuilder.equal(root.get("propertyType"), propertyType);
        };
    }
}
