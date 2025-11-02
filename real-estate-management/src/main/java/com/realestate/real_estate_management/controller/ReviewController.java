package com.realestate.real_estate_management.controller;

import com.realestate.real_estate_management.entity.Review;
import com.realestate.real_estate_management.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;

@RestController
@RequestMapping("/api")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/properties/{propertyId}/reviews")
    public ResponseEntity<Review> createReview(
            @PathVariable Long propertyId,
            @RequestBody Review review,
            Principal principal) {
        
        String userEmail = principal.getName(); 
        Review savedReview = reviewService.createReview(propertyId, userEmail, review);
        
        return ResponseEntity.ok(savedReview);
    }
    
}