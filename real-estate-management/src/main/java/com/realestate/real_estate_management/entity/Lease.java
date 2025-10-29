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
@Table(name = "Lease") 
public class Lease {

    @EmbeddedId 
    private LeaseKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("tenantId") 
    @JoinColumn(name = "Borrowed_By")
    private Tenant tenant;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("propertyId") 
    @JoinColumn(name = "Property_leased")
    private Property property;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("sellerId") 
    @JoinColumn(name = "Lend_By")
    private Seller seller;

    @Column(name = "Amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "Mode_of_payment", nullable = false)
    private String modeOfPayment;

    @Column(name = "Remarks")
    private String remarks;

    public Lease() {
    }

    public LeaseKey getId() {
        return id;
    }

    public void setId(LeaseKey id) {
        this.id = id;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getModeOfPayment() {
        return modeOfPayment;
    }

    public void setModeOfPayment(String modeOfPayment) {
        this.modeOfPayment = modeOfPayment;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
}