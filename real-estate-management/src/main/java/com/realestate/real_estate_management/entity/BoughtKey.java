package com.realestate.real_estate_management.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BoughtKey implements Serializable {

    @Column(name = "Boughtby")
    private Long buyerId;

    @Column(name = "Soldby")
    private Long sellerId;

    @Column(name = "WhichProperty")
    private Long propertyId;

    public BoughtKey() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoughtKey boughtKey = (BoughtKey) o;
        return Objects.equals(buyerId, boughtKey.buyerId) &&
               Objects.equals(sellerId, boughtKey.sellerId) &&
               Objects.equals(propertyId, boughtKey.propertyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(buyerId, sellerId, propertyId);
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

}