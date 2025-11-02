package com.realestate.real_estate_management.service;

import com.realestate.real_estate_management.entity.Property;
import com.realestate.real_estate_management.entity.Review;
import com.realestate.real_estate_management.entity.ReviewKey; 
import com.realestate.real_estate_management.entity.User;
import com.realestate.real_estate_management.repository.PropertyRepository;
import com.realestate.real_estate_management.repository.ReviewRepository;
import com.realestate.real_estate_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private UserRepository userRepository;

    public Review createReview(Long propertyId, String userEmail, Review reviewDetails) {

        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found with id: " + propertyId));

        User user = userRepository.findByEmail(userEmail);
        if (user == null) {
            throw new RuntimeException("User not found with email: " + userEmail);
        }

        Review newReview = new Review();
        
        newReview.setId(new ReviewKey());

        newReview.setProperty(property);
        newReview.setUser(user);
        
        newReview.setRating(reviewDetails.getRating());
        newReview.setComments(reviewDetails.getComments());
        newReview.setReviewDate(LocalDate.now());

        return reviewRepository.save(newReview);
    }
}