package com.realestate.real_estate_management.repository;

import com.realestate.real_estate_management.entity.Property;
import com.realestate.real_estate_management.entity.Review;
import com.realestate.real_estate_management.entity.ReviewKey;
import com.realestate.real_estate_management.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, ReviewKey> {
    void deleteAllByUser(User user);
    void deleteAllByProperty(Property property);
}