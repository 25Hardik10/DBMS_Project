package com.realestate.real_estate_management.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "flat") 
@PrimaryKeyJoinColumn(name = "FlatID") 
public class Flat extends Property {

    @Column(name = "FloorNo")
    private Integer floorNumber;

    @Column(name = "BHK", nullable = false)
    private Integer bhk;

    @Column(name = "bathrooms", nullable = false)
    private Integer bathrooms;

    @Column(name = "parking")
    private Boolean parking;

    @Column(name = "has_lift")
    private Boolean hasLift;

    @Column(name = "maintainencecharge")
    private BigDecimal maintenanceCharge;

    @Column(name = "furnishingstatus")
    private String furnishingStatus;

    @Column(name = "built_area", nullable = false)
    private Integer builtArea;

    @Column(name = "carpet_area", nullable = false)
    private Integer carpetArea;

    public Flat() {
    }

    public Integer getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(Integer floorNumber) {
        this.floorNumber = floorNumber;
    }

    public Integer getBhk() {
        return bhk;
    }

    public void setBhk(Integer bhk) {
        this.bhk = bhk;
    }

    public Integer getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(Integer bathrooms) {
        this.bathrooms = bathrooms;
    }

    public Boolean getParking() {
        return parking;
    }

    public void setParking(Boolean parking) {
        this.parking = parking;
    }

    public Boolean getHasLift() {
        return hasLift;
    }

    public void setHasLift(Boolean hasLift) {
        this.hasLift = hasLift;
    }

    public BigDecimal getMaintenanceCharge() {
        return maintenanceCharge;
    }

    public void setMaintenanceCharge(BigDecimal maintenanceCharge) {
        this.maintenanceCharge = maintenanceCharge;
    }

    public String getFurnishingStatus() {
        return furnishingStatus;
    }

    public void setFurnishingStatus(String furnishingStatus) {
        this.furnishingStatus = furnishingStatus;
    }

    public Integer getBuiltArea() {
        return builtArea;
    }

    public void setBuiltArea(Integer builtArea) {
        this.builtArea = builtArea;
    }

    public Integer getCarpetArea() {
        return carpetArea;
    }

    public void setCarpetArea(Integer carpetArea) {
        this.carpetArea = carpetArea;
    }

}