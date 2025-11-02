package com.realestate.real_estate_management.service;

import com.realestate.real_estate_management.entity.Flat;
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

    public Flat saveFlat(Flat flat, Principal principal) {
        Seller seller = sellerRepository.findByEmail(principal.getName());
        if (seller == null) {
            throw new RuntimeException("Seller profile not found for authenticated user.");
        }
        
        flat.setSeller(seller);
        
        return flatRepository.save(flat);
    }
    public Optional<Flat> updateFlat(Long id, Flat flatDetails, String sellerEmail) {
        return flatRepository.findById(id)
            .map(existingFlat -> {
                
                // 1. CHECK OWNERSHIP
                if (!Objects.equals(existingFlat.getSeller().getEmail(), sellerEmail)) {
                    throw new AccessDeniedException("You do not have permission to update this property.");
                }

                // 2. UPDATE ALL FIELDS (This was our previous fix)
                existingFlat.setListedDate(flatDetails.getListedDate());
                existingFlat.setPrice(flatDetails.getPrice());
                existingFlat.setPropertyStatus(flatDetails.getPropertyStatus());
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
                
                // Note: We don't need to handle amenities here,
                // as the CascadeType.ALL on the Property entity
                // *should* handle the update. If it doesn't,
                // we'd need to add logic to clear and re-add amenities.
                existingFlat.setAmenities(flatDetails.getAmenities());

                return flatRepository.save(existingFlat);
            });
    }
    public Optional<Flat> updateFlat(Long id, Flat flatDetails) {
        return flatRepository.findById(id)
            .map(existingFlat -> {
                
                existingFlat.setListedDate(flatDetails.getListedDate());
                existingFlat.setPrice(flatDetails.getPrice());
                existingFlat.setPropertyStatus(flatDetails.getPropertyStatus());
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

                return flatRepository.save(existingFlat);
            });
    }
}