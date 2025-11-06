package com.realestate.real_estate_management.service;

import com.realestate.real_estate_management.entity.Flat;
import com.realestate.real_estate_management.entity.Image;
import com.realestate.real_estate_management.entity.Seller;
import com.realestate.real_estate_management.repository.FlatRepository;
import com.realestate.real_estate_management.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.security.Principal;

@Service
@Transactional
public class FlatService {

    @Autowired
    private FlatRepository flatRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private PropertyService propertyService;

    public Flat saveFlat(Flat flat, Principal principal) {
        Seller seller = sellerRepository.findByEmail(principal.getName());
        if (seller == null) {
            throw new RuntimeException("Seller profile not found for authenticated user.");
        }

        flat.setSeller(seller);
        flat.setPropertyType("Flat");

        // ✅ Fix: set default status if null
        if (flat.getPropertyStatus() == null) {
            flat.setPropertyStatus("Available"); // default value
        }

        // ✅ Ensure each image has property reference
        if (flat.getImages() != null) {
            for (Image image : flat.getImages()) {
                image.setProperty(flat);
            }
        }

        return (Flat) propertyService.saveProperty(flat);
    }

    public Optional<Flat> updateFlat(Long id, Flat flatDetails, String sellerEmail) {
        return flatRepository.findById(id)
                .map(existingFlat -> {
                    if (!Objects.equals(existingFlat.getSeller().getEmail(), sellerEmail)) {
                        throw new AccessDeniedException("You do not have permission to update this property.");
                    }

                    existingFlat.setListedDate(flatDetails.getListedDate());
                    existingFlat.setPrice(flatDetails.getPrice());
                    existingFlat.setPropertyStatus(
                            flatDetails.getPropertyStatus() != null ? flatDetails.getPropertyStatus() : "Available"
                    );
                    existingFlat.setAddress(flatDetails.getAddress());
                    existingFlat.setCity(flatDetails.getCity());
                    existingFlat.setState(flatDetails.getState());
                    existingFlat.setPin(flatDetails.getPin());
                    existingFlat.setDescription(flatDetails.getDescription());

                    existingFlat.setFloorNumber(flatDetails.getFloorNumber());
                    existingFlat.setBhk(flatDetails.getBhk());
                    existingFlat.setBathrooms(flatDetails.getBathrooms());
                    existingFlat.setParking(flatDetails.getParking());
                    existingFlat.setHasLift(flatDetails.getHasLift());
                    existingFlat.setMaintenanceCharge(flatDetails.getMaintenanceCharge());
                    existingFlat.setFurnishingStatus(flatDetails.getFurnishingStatus());
                    existingFlat.setBuiltArea(flatDetails.getBuiltArea());
                    existingFlat.setCarpetArea(flatDetails.getCarpetArea());

                    existingFlat.setAmenities(flatDetails.getAmenities());
                    existingFlat.setImages(flatDetails.getImages());
                    if (existingFlat.getImages() != null) {
                        for (Image image : existingFlat.getImages()) {
                            image.setProperty(existingFlat);
                        }
                    }

                    return flatRepository.save(existingFlat);
                });
    }

    public Optional<Flat> updateFlat(Long id, Flat flatDetails) {
        return flatRepository.findById(id)
                .map(existingFlat -> {
                    existingFlat.setListedDate(flatDetails.getListedDate());
                    existingFlat.setPrice(flatDetails.getPrice());
                    existingFlat.setPropertyStatus(
                            flatDetails.getPropertyStatus() != null ? flatDetails.getPropertyStatus() : "Available"
                    );
                    existingFlat.setAddress(flatDetails.getAddress());
                    existingFlat.setCity(flatDetails.getCity());
                    existingFlat.setState(flatDetails.getState());
                    existingFlat.setPin(flatDetails.getPin());
                    existingFlat.setDescription(flatDetails.getDescription());

                    existingFlat.setFloorNumber(flatDetails.getFloorNumber());
                    existingFlat.setBhk(flatDetails.getBhk());
                    existingFlat.setBathrooms(flatDetails.getBathrooms());
                    existingFlat.setParking(flatDetails.getParking());
                    existingFlat.setHasLift(flatDetails.getHasLift());
                    existingFlat.setMaintenanceCharge(flatDetails.getMaintenanceCharge());
                    existingFlat.setFurnishingStatus(flatDetails.getFurnishingStatus());
                    existingFlat.setBuiltArea(flatDetails.getBuiltArea());
                    existingFlat.setCarpetArea(flatDetails.getCarpetArea());

                    existingFlat.setAmenities(flatDetails.getAmenities());
                    existingFlat.setImages(flatDetails.getImages());
                    if (existingFlat.getImages() != null) {
                        for (Image image : existingFlat.getImages()) {
                            image.setProperty(existingFlat);
                        }
                    }

                    return flatRepository.save(existingFlat);
                });
    }
}
