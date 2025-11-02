package com.realestate.real_estate_management.service;

import com.realestate.real_estate_management.entity.Land;
import com.realestate.real_estate_management.entity.Seller;
import com.realestate.real_estate_management.repository.LandRepository;
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
public class LandService {

    @Autowired
    private LandRepository landRepository;

    @Autowired
    private SellerRepository sellerRepository;

    public Land saveLand(Land land, Principal principal) {
        Seller seller = sellerRepository.findByEmail(principal.getName());
        if (seller == null) {
            throw new RuntimeException("Seller profile not found for authenticated user.");
        }
        land.setSeller(seller);
        return landRepository.save(land);
    }
    public Optional<Land> updateLand(Long id, Land landDetails, String sellerEmail) {
        return landRepository.findById(id)
            .map(existingLand -> {
                
                // 1. CHECK OWNERSHIP
                if (!Objects.equals(existingLand.getSeller().getEmail(), sellerEmail)) {
                    throw new AccessDeniedException("You do not have permission to update this property.");
                }

                // 2. UPDATE ALL FIELDS
                // Base Property fields
                existingLand.setListedDate(landDetails.getListedDate());
                existingLand.setPrice(landDetails.getPrice());
                existingLand.setPropertyStatus(landDetails.getPropertyStatus());
                existingLand.setAddress(landDetails.getAddress());
                existingLand.setCity(landDetails.getCity());
                existingLand.setState(landDetails.getState());
                existingLand.setPin(landDetails.getPin());
                existingLand.setDescription(landDetails.getDescription());
                
                // Land specific fields
                existingLand.setLandArea(landDetails.getLandArea());
                existingLand.setHasFence(landDetails.getHasFence());
                existingLand.setHasRoad(landDetails.getHasRoad());
                existingLand.setZoningType(landDetails.getZoningType());

                // Update amenities
                existingLand.setAmenities(landDetails.getAmenities());

                return landRepository.save(existingLand);
            });
    }
    public Optional<Land> updateLand(Long id, Land LandDetails) {
        return landRepository.findById(id)
            .map(existingLand -> {
                existingLand.setListedDate(LandDetails.getListedDate());
                existingLand.setPrice(LandDetails.getPrice());
                existingLand.setPropertyStatus(LandDetails.getPropertyStatus());
                existingLand.setAddress(LandDetails.getAddress());
                existingLand.setCity(LandDetails.getCity());
                existingLand.setState(LandDetails.getState());
                existingLand.setPin(LandDetails.getPin());
                existingLand.setDescription(LandDetails.getDescription());

                existingLand.setLandArea(LandDetails.getLandArea());
                existingLand.setHasFence(LandDetails.getHasFence());
                existingLand.setHasRoad(LandDetails.getHasRoad());
                existingLand.setZoningType(LandDetails.getZoningType());

                return landRepository.save(existingLand);
            });
    }
}