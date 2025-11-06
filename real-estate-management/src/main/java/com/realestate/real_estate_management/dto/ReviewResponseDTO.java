package com.realestate.real_estate_management.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ReviewResponseDTO {
    private String userName;
    private BigDecimal rating;
    private String comments;
    private LocalDate reviewDate;

    public ReviewResponseDTO(String userName, BigDecimal rating, String comments, LocalDate reviewDate) {
        this.userName = userName;
        this.rating = rating;
        this.comments = comments;
        this.reviewDate = reviewDate;
    }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public BigDecimal getRating() { return rating; }
    public void setRating(BigDecimal rating) { this.rating = rating; }

    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }

    public LocalDate getReviewDate() { return reviewDate; }
    public void setReviewDate(LocalDate reviewDate) { this.reviewDate = reviewDate; }
}
