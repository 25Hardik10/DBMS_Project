package com.realestate.real_estate_management.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "Image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ImageID")
    private Long imageId;

    @Column(name = "UploadDate", nullable = false)
    private LocalDate uploadDate;

    @Column(name = "URL", nullable = false, length = 500)
    private String url;

    @Column(name = "Caption", length = 150)
    private String caption;

    // --- NEW FIELD ---
    @Column(name = "image_order") // Database column will be named 'image_order'
    private Integer imageOrder;
    // --- END NEW FIELD ---

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ImageOf", nullable = false)
    private Property property;

    public Image() {
    }

    // --- Standard Getters and Setters below ---

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public LocalDate getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDate uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    // --- GETTER AND SETTER FOR NEW FIELD ---
    public Integer getImageOrder() {
        return imageOrder;
    }

    public void setImageOrder(Integer imageOrder) {
        this.imageOrder = imageOrder;
    }
    // --- END GETTER AND SETTER ---
}