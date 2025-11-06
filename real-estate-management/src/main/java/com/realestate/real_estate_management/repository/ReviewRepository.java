package com.realestate.real_estate_management.repository;

import com.realestate.real_estate_management.entity.Property;
import com.realestate.real_estate_management.entity.Review;
import com.realestate.real_estate_management.entity.ReviewKey;
import com.realestate.real_estate_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, ReviewKey> {
    void deleteAllByUser(User user);
    void deleteAllByProperty(Property property);
    @Query("SELECT r FROM Review r JOIN FETCH r.user WHERE r.property.propertyId = :propertyId")
    List<Review> findByPropertyId(@Param("propertyId") Long propertyId);
    List<Review> findByProperty_PropertyId(Long propertyId);
}