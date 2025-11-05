package com.realestate.real_estate_management.service;

import com.realestate.real_estate_management.dto.ReviewRequest;
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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private UserRepository userRepository;

    // Use DTO
    public Review createReview(Long propertyId, String userEmail, ReviewRequest request) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found with id: " + propertyId));

        User user = userRepository.findByEmail(userEmail);
        if (user == null) {
            throw new RuntimeException("User not found with email: " + userEmail);
        }

        // NOTE: ReviewKey constructor order is (userId, propertyId)
        ReviewKey key = new ReviewKey(user.getUserId(), propertyId);

        Optional<Review> existing = reviewRepository.findById(key);
        if (existing.isPresent()) {
            throw new RuntimeException("You have already reviewed this property.");
        }

        Review newReview = new Review();
        newReview.setId(key);            // set composite key
        newReview.setUser(user);         // set user
        newReview.setProperty(property); // set property

        if (request.getRating() == null) {
            throw new RuntimeException("Rating is required");
        }
        newReview.setRating(BigDecimal.valueOf(request.getRating())); // convert to BigDecimal
        newReview.setComments(request.getComments());
        newReview.setReviewDate(LocalDate.now());

        return reviewRepository.save(newReview);
    }

    // keep addOrUpdateReview if you want updates; ensure ReviewKey order here too
}
