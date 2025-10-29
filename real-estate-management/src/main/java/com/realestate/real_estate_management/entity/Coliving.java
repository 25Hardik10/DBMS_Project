package com.realestate.real_estate_management.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "Coliving")
@PrimaryKeyJoinColumn(name = "ColivingID") 
public class Coliving extends Property {

    @Column(name = "room_type", nullable = false)
    private String roomType;

    @Column(name = "gender_pref", nullable = false)
    private String genderPreference;

    @Column(name = "Rent_PAX", nullable = false)
    private Integer rentPerPerson;

    @Column(name = "total_rooms", nullable = false)
    private Integer totalRooms;

    @Column(name = "available_rooms", nullable = false)
    private Integer availableRooms;

    @Column(name = "cleaning")
    private Boolean cleaning;

    public Coliving() {
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getGenderPreference() {
        return genderPreference;
    }

    public void setGenderPreference(String genderPreference) {
        this.genderPreference = genderPreference;
    }

    public Integer getRentPerPerson() {
        return rentPerPerson;
    }

    public void setRentPerPerson(Integer rentPerPerson) {
        this.rentPerPerson = rentPerPerson;
    }

    public Integer getTotalRooms() {
        return totalRooms;
    }

    public void setTotalRooms(Integer totalRooms) {
        this.totalRooms = totalRooms;
    }

    public Integer getAvailableRooms() {
        return availableRooms;
    }

    public void setAvailableRooms(Integer availableRooms) {
        this.availableRooms = availableRooms;
    }

    public Boolean getCleaning() {
        return cleaning;
    }

    public void setCleaning(Boolean cleaning) {
        this.cleaning = cleaning;
    }

}