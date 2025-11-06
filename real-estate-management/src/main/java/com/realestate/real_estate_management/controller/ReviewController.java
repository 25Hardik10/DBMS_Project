package com.realestate.real_estate_management.controller;

import com.realestate.real_estate_management.dto.ReviewRequest;
import com.realestate.real_estate_management.dto.ReviewResponseDTO;
import com.realestate.real_estate_management.entity.Review;
import com.realestate.real_estate_management.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    // ✅ Create a review
    @PostMapping("/properties/{propertyId}/reviews")
    public ResponseEntity<?> createReview(
            @PathVariable Long propertyId,
            @RequestBody ReviewRequest request,
            Principal principal) {

        if (principal == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        try {
            Review saved = reviewService.createReview(propertyId, principal.getName(), request);
            return ResponseEntity.ok(saved);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    // ✅ Get all reviews for a property (with reviewer names)
    @GetMapping("/properties/{propertyId}/reviews")
    public ResponseEntity<List<ReviewResponseDTO>> getReviewsByProperty(@PathVariable Long propertyId) {
        List<ReviewResponseDTO> reviews = reviewService.getReviewsByProperty(propertyId);
        return ResponseEntity.ok(reviews);
    }
}
