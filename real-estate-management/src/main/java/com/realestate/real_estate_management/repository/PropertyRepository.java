package com.realestate.real_estate_management.repository;

import com.realestate.real_estate_management.entity.Property;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long>, JpaSpecificationExecutor<Property> {
    
    @Override
    @EntityGraph(attributePaths = {"seller"})
    List<Property> findAll();

    @Override
    @EntityGraph(attributePaths = {"seller"})
    List<Property> findAll(Specification<Property> spec);

    List<Property> findBySeller_Email(String email);
}