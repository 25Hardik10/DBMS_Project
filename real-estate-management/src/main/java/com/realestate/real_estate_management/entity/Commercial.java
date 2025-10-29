package com.realestate.real_estate_management.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "commercial")
@PrimaryKeyJoinColumn(name = "CommercialID")
public class Commercial extends Property {

    @Column(name = "commercial_type", nullable = false)
    private String commercialType;

    @Column(name = "built_area", nullable = false)
    private Integer builtArea;

    @Column(name = "carpet_area", nullable = false)
    private Integer carpetArea;

    @Column(name = "floor_no")
    private Integer floorNumber;

    @Column(name = "parking_space")
    private Boolean parkingSpace;

    @Column(name = "washrooms")
    private Integer washrooms;

    public Commercial() {
    }

    public String getCommercialType() {
        return commercialType;
    }

    public void setCommercialType(String commercialType) {
        this.commercialType = commercialType;
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

    public Integer getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(Integer floorNumber) {
        this.floorNumber = floorNumber;
    }

    public Boolean getParkingSpace() {
        return parkingSpace;
    }

    public void setParkingSpace(Boolean parkingSpace) {
        this.parkingSpace = parkingSpace;
    }

    public Integer getWashrooms() {
        return washrooms;
    }

    public void setWashrooms(Integer washrooms) {
        this.washrooms = washrooms;
    }

}