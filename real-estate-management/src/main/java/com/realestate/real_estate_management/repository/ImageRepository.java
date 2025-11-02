package com.realestate.real_estate_management.repository;

import com.realestate.real_estate_management.entity.Image;
import com.realestate.real_estate_management.entity.Property;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByProperty_PropertyIdOrderByImageOrderAsc(Long propertyId);
    void deleteAllByProperty(Property property);
}