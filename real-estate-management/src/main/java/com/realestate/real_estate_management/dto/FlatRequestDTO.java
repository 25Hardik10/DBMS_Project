package com.realestate.real_estate_management.dto;

import com.realestate.real_estate_management.entity.Flat;
import com.realestate.real_estate_management.entity.Image;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class FlatRequestDTO {
    private BigDecimal price;
    private String address;
    private String city;
    private String state;
    private Integer pin;
    private String description;
    private Integer bhk;
    private Integer bathrooms;
    private Integer floorNumber;
    private Integer builtArea;
    private Integer carpetArea;
    private BigDecimal maintenanceCharge;
    private String furnishingStatus;
    private Boolean parking;
    private Boolean hasLift;
    private List<String> imageUrls; // 👈 new field for images

    // ✅ Convert DTO to Flat entity
    public Flat toEntity() {
        Flat flat = new Flat();
        flat.setListedDate(LocalDate.now());
        flat.setPrice(price);
        flat.setAddress(address);
        flat.setCity(city);
        flat.setState(state);
        flat.setPin(pin);
        flat.setDescription(description);
        flat.setBhk(bhk);
        flat.setBathrooms(bathrooms);
        flat.setFloorNumber(floorNumber);
        flat.setBuiltArea(builtArea);
        flat.setCarpetArea(carpetArea);
        flat.setMaintenanceCharge(maintenanceCharge);
        flat.setFurnishingStatus(furnishingStatus);
        flat.setParking(parking);
        flat.setHasLift(hasLift);

        if (imageUrls != null && !imageUrls.isEmpty()) {
            List<Image> images = imageUrls.stream()
                .filter(url -> url != null && !url.trim().isEmpty())
                .map(url -> {
                    Image img = new Image();
                    img.setUrl(url.trim());
                    img.setUploadDate(LocalDate.now());
                    return img;
                })
                .collect(Collectors.toList());
            flat.setImages(images);
        }

        return flat;
    }
    // Getters & Setters
    public List<String> getImageUrls() { return imageUrls; }
    public void setImageUrls(List<String> imageUrls) { this.imageUrls = imageUrls; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    public Integer getPin() { return pin; }
    public void setPin(Integer pin) { this.pin = pin; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Integer getBhk() { return bhk; }
    public void setBhk(Integer bhk) { this.bhk = bhk; }
    public Integer getBathrooms() { return bathrooms; }
    public void setBathrooms(Integer bathrooms) { this.bathrooms = bathrooms; }
    public Integer getFloorNumber() { return floorNumber; }
    public void setFloorNumber(Integer floorNumber) { this.floorNumber = floorNumber; }
    public Integer getBuiltArea() { return builtArea; }
    public void setBuiltArea(Integer builtArea) { this.builtArea = builtArea; }
    public Integer getCarpetArea() { return carpetArea; }
    public void setCarpetArea(Integer carpetArea) { this.carpetArea = carpetArea; }
    public BigDecimal getMaintenanceCharge() { return maintenanceCharge; }
    public void setMaintenanceCharge(BigDecimal maintenanceCharge) { this.maintenanceCharge = maintenanceCharge; }
    public String getFurnishingStatus() { return furnishingStatus; }
    public void setFurnishingStatus(String furnishingStatus) { this.furnishingStatus = furnishingStatus; }
    public Boolean getParking() { return parking; }
    public void setParking(Boolean parking) { this.parking = parking; }
    public Boolean getHasLift() { return hasLift; }
    public void setHasLift(Boolean hasLift) { this.hasLift = hasLift; }
}
