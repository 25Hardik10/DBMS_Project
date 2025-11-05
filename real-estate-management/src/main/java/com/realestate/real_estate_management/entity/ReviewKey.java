package com.realestate.real_estate_management.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable 
public class ReviewKey implements Serializable {

    @Column(name = "reviewed_by")   // ✅ lowercase and matches DB
    private Long userId;

    @Column(name = "reviewed_prop") // ✅ lowercase and matches DB
    private Long propertyId;

    public ReviewKey() {}

    public ReviewKey(Long userId, Long propertyId) {
        this.userId = userId;
        this.propertyId = propertyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReviewKey reviewKey = (ReviewKey) o;
        return Objects.equals(userId, reviewKey.userId) &&
               Objects.equals(propertyId, reviewKey.propertyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, propertyId);
    }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getPropertyId() { return propertyId; }
    public void setPropertyId(Long propertyId) { this.propertyId = propertyId; }
}


