package com.realestate.real_estate_management.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "rental")
@PrimaryKeyJoinColumn(name = "rentalID") 
public class Rental extends Property {

    @Column(name = "rent")
    private BigDecimal rent;

    @Column(name = "Lease_term", nullable = false)
    private Integer leaseTerm;

    @Column(name = "furnishing_status")
    private String furnishingStatus;

    @Column(name = "pet_allowed")
    private Boolean petAllowed;

    @Column(name = "deposit")
    private BigDecimal deposit;

    @Column(name = "available_from")
    private LocalDate availableFrom;

    @Column(name = "tenant_type")
    private String tenantType;

    public Rental() {
    }

    public BigDecimal getRent() {
        return rent;
    }

    public void setRent(BigDecimal rent) {
        this.rent = rent;
    }

    public Integer getLeaseTerm() {
        return leaseTerm;
    }

    public void setLeaseTerm(Integer leaseTerm) {
        this.leaseTerm = leaseTerm;
    }

    public String getFurnishingStatus() {
        return furnishingStatus;
    }

    public void setFurnishingStatus(String furnishingStatus) {
        this.furnishingStatus = furnishingStatus;
    }

    public Boolean getPetAllowed() {
        return petAllowed;
    }

    public void setPetAllowed(Boolean petAllowed) {
        this.petAllowed = petAllowed;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public LocalDate getAvailableFrom() {
        return availableFrom;
    }

    public void setAvailableFrom(LocalDate availableFrom) {
        this.availableFrom = availableFrom;
    }

    public String getTenantType() {
        return tenantType;
    }

    public void setTenantType(String tenantType) {
        this.tenantType = tenantType;
    }

}