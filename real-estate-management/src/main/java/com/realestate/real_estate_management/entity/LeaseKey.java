package com.realestate.real_estate_management.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class LeaseKey implements Serializable {

    @Column(name = "Borrowed_By")
    private Long tenantId;

    @Column(name = "Property_leased")
    private Long propertyId;

    @Column(name = "Lend_By")
    private Long sellerId;

    public LeaseKey() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LeaseKey leaseKey = (LeaseKey) o;
        return Objects.equals(tenantId, leaseKey.tenantId) &&
               Objects.equals(propertyId, leaseKey.propertyId) &&
               Objects.equals(sellerId, leaseKey.sellerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tenantId, propertyId, sellerId);
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

}