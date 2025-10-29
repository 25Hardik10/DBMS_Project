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
@Table(name = "Bought") 
public class Bought {

    @EmbeddedId 
    private BoughtKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("buyerId") 
    @JoinColumn(name = "Boughtby")
    private Buyer buyer;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("sellerId")
    @JoinColumn(name = "Soldby")
    private Seller seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("propertyId")
    @JoinColumn(name = "WhichProperty")
    private Property property;

    // --- 3. Map the other columns ---

    @Column(name = "Buyingdate", nullable = false)
    private LocalDate buyingDate;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "ModeofPayment", nullable = false)
    private String modeOfPayment;

    public Bought() {
    }

    public BoughtKey getId() {
        return id;
    }

    public void setId(BoughtKey id) {
        this.id = id;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public LocalDate getBuyingDate() {
        return buyingDate;
    }

    public void setBuyingDate(LocalDate buyingDate) {
        this.buyingDate = buyingDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getModeOfPayment() {
        return modeOfPayment;
    }

    public void setModeOfPayment(String modeOfPayment) {
        this.modeOfPayment = modeOfPayment;
    }

}