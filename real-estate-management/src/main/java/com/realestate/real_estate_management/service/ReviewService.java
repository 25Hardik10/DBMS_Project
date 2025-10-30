package com.realestate.real_estate_management.service;

import com.realestate.real_estate_management.entity.Property;
import com.realestate.real_estate_management.entity.Review;
import com.realestate.real_estate_management.entity.ReviewKey; // <-- Make sure this is imported
import com.realestate.real_estate_management.entity.User;
import com.realestate.real_estate_management.repository.PropertyRepository;
import com.realestate.real_estate_management.repository.ReviewRepository;
import com.realestate.real_estate_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private UserRepository userRepository;

    public Review createReview(Long propertyId, String userEmail, Review reviewDetails) {

        // 1. Fetch the related Property
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found with id: " + propertyId));

        // 2. Fetch the related User
        User user = userRepository.findByEmail(userEmail);
        if (user == null) {
            throw new RuntimeException("User not found with email: " + userEmail);
        }

        // 3. Build the complete Review object
        Review newReview = new Review();
        
        // 4. CRITICAL FIX: Instantiate the EmbeddedId
        newReview.setId(new ReviewKey());

        // 5. Set the relationships
        // Hibernate will now use these objects to populate the fields in the ReviewKey
        newReview.setProperty(property);
        newReview.setUser(user);
        
        // 6. Set details from the request
        newReview.setRating(reviewDetails.getRating());
        newReview.setComments(reviewDetails.getComments());
        newReview.setReviewDate(LocalDate.now());

        // 7. Save the new review
        return reviewRepository.save(newReview);
    }
}