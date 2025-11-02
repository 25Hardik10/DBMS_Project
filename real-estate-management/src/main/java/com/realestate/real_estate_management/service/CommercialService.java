package com.realestate.real_estate_management.service;

import com.realestate.real_estate_management.entity.Commercial;
import com.realestate.real_estate_management.entity.Seller; 
import com.realestate.real_estate_management.repository.CommercialRepository;
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
public class CommercialService {

    @Autowired
    private CommercialRepository commercialRepository;

    @Autowired
    private SellerRepository sellerRepository;

    public Commercial saveCommercial(Commercial commercial, Principal principal) {
        Seller seller = sellerRepository.findByEmail(principal.getName());
        if (seller == null) {
            throw new RuntimeException("Seller profile not found for authenticated user.");
        }
        commercial.setSeller(seller);
        return commercialRepository.save(commercial);
    }
    public Optional<Commercial> updateCommercial(Long id, Commercial commercialDetails, String sellerEmail) {
        return commercialRepository.findById(id)
            .map(existingCommercial -> {

                // 1. CHECK OWNERSHIP
                if (!Objects.equals(existingCommercial.getSeller().getEmail(), sellerEmail)) {
                    throw new AccessDeniedException("You do not have permission to update this property.");
                }

                // 2. UPDATE ALL FIELDS
                // Base Property fields
                existingCommercial.setListedDate(commercialDetails.getListedDate());
                existingCommercial.setPrice(commercialDetails.getPrice());
                existingCommercial.setPropertyStatus(commercialDetails.getPropertyStatus());
                existingCommercial.setAddress(commercialDetails.getAddress());
                existingCommercial.setCity(commercialDetails.getCity());
                existingCommercial.setState(commercialDetails.getState());
                existingCommercial.setPin(commercialDetails.getPin());
                existingCommercial.setDescription(commercialDetails.getDescription());
                
                // Commercial specific fields
                existingCommercial.setCommercialType(commercialDetails.getCommercialType());
                existingCommercial.setBuiltArea(commercialDetails.getBuiltArea());
                existingCommercial.setCarpetArea(commercialDetails.getCarpetArea());
                existingCommercial.setFloorNumber(commercialDetails.getFloorNumber());
                existingCommercial.setParkingSpace(commercialDetails.getParkingSpace());
                existingCommercial.setWashrooms(commercialDetails.getWashrooms());

                // Update amenities
                existingCommercial.setAmenities(commercialDetails.getAmenities());

                return commercialRepository.save(existingCommercial);
            });
    }
    public Optional<Commercial> updateCommercial(Long id, Commercial CommercialDetails) {
        return commercialRepository.findById(id)
            .map(existingCommercial -> {
                existingCommercial.setListedDate(CommercialDetails.getListedDate());
                existingCommercial.setPrice(CommercialDetails.getPrice());
                existingCommercial.setPropertyStatus(CommercialDetails.getPropertyStatus());
                existingCommercial.setAddress(CommercialDetails.getAddress());
                existingCommercial.setCity(CommercialDetails.getCity());
                existingCommercial.setState(CommercialDetails.getState());
                existingCommercial.setPin(CommercialDetails.getPin());
                existingCommercial.setDescription(CommercialDetails.getDescription());

                existingCommercial.setBuiltArea(CommercialDetails.getBuiltArea());
                existingCommercial.setCarpetArea(CommercialDetails.getCarpetArea());
                existingCommercial.setFloorNumber(CommercialDetails.getFloorNumber());
                existingCommercial.setParkingSpace(CommercialDetails.getParkingSpace());
                existingCommercial.setWashrooms(CommercialDetails.getWashrooms());

                return commercialRepository.save(existingCommercial);
            });
    }
}