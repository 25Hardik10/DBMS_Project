package com.realestate.real_estate_management.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "land") 
@PrimaryKeyJoinColumn(name = "LandID") 
public class Land extends Property {

    @Column(name = "Land_area", nullable = false)
    private Integer landArea;

    @Column(name = "has_fence")
    private Boolean hasFence;

    @Column(name = "has_road")
    private Boolean hasRoad;

    @Column(name = "zoning_type", nullable = false)
    private String zoningType;

    public Land() {
    }

    public Integer getLandArea() {
        return landArea;
    }

    public void setLandArea(Integer landArea) {
        this.landArea = landArea;
    }

    public Boolean getHasFence() {
        return hasFence;
    }

    public void setHasFence(Boolean hasFence) {
        this.hasFence = hasFence;
    }

    public Boolean getHasRoad() {
        return hasRoad;
    }

    public void setHasRoad(Boolean hasRoad) {
        this.hasRoad = hasRoad;
    }

    public String getZoningType() {
        return zoningType;
    }

    public void setZoningType(String zoningType) {
        this.zoningType = zoningType;
    }

}