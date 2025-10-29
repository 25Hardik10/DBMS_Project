package com.realestate.real_estate_management.repository;

import com.realestate.real_estate_management.entity.Review;
import com.realestate.real_estate_management.entity.ReviewKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, ReviewKey> {

}