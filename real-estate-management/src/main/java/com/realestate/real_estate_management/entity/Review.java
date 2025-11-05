package com.realestate.real_estate_management.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "review") // lowercase to match your DB
public class Review {

    @EmbeddedId
    private ReviewKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")              // must match field name in ReviewKey
    @JoinColumn(name = "reviewed_by")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("propertyId")          // must match field name in ReviewKey
    @JoinColumn(name = "reviewed_prop")
    private Property property;

    @Column(name = "comments", length = 500)
    private String comments;

    @Column(name = "rating", nullable = false)
    private BigDecimal rating;

    @Column(name = "review_date", nullable = false)
    private LocalDate reviewDate;

    public Review() {}

    public ReviewKey getId() { return id; }
    public void setId(ReviewKey id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Property getProperty() { return property; }
    public void setProperty(Property property) { this.property = property; }

    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }

    public BigDecimal getRating() { return rating; }
    public void setRating(BigDecimal rating) { this.rating = rating; }

    public LocalDate getReviewDate() { return reviewDate; }
    public void setReviewDate(LocalDate reviewDate) { this.reviewDate = reviewDate; }
}
