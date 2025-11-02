package com.realestate.real_estate_management.service;

import java.security.Principal;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.realestate.real_estate_management.entity.Coliving;
import com.realestate.real_estate_management.entity.Seller;
import com.realestate.real_estate_management.repository.ColivingRepository;
import com.realestate.real_estate_management.repository.SellerRepository; 

@Service
@Transactional
public class ColivingService {

    @Autowired
    private ColivingRepository ColivingRepository;

    @Autowired
    private SellerRepository sellerRepository;
    
    public Coliving saveColiving(Coliving coliving, Principal principal) {
        Seller seller = sellerRepository.findByEmail(principal.getName());
        if (seller == null) {
            throw new RuntimeException("Seller profile not found for authenticated user.");
        }
        coliving.setSeller(seller);
        return ColivingRepository.save(coliving);
    }
    public Optional<Coliving> updateColiving(Long id, Coliving ColivingDetails, String sellerEmail) {
        return ColivingRepository.findById(id)
            .map(existingColiving -> {

                // 1. CHECK OWNERSHIP
                if (!Objects.equals(existingColiving.getSeller().getEmail(), sellerEmail)) {
                    throw new AccessDeniedException("You do not have permission to update this property.");
                }

                // 2. UPDATE ALL FIELDS
                // Base Property fields
                existingColiving.setListedDate(ColivingDetails.getListedDate());
                existingColiving.setPrice(ColivingDetails.getPrice());
                existingColiving.setPropertyStatus(ColivingDetails.getPropertyStatus());
                existingColiving.setAddress(ColivingDetails.getAddress());
                existingColiving.setCity(ColivingDetails.getCity());
                existingColiving.setState(ColivingDetails.getState());
                existingColiving.setPin(ColivingDetails.getPin());
                existingColiving.setDescription(ColivingDetails.getDescription());
                
                // Coliving specific fields
                existingColiving.setRoomType(ColivingDetails.getRoomType());
                existingColiving.setGenderPreference(ColivingDetails.getGenderPreference());
                existingColiving.setRentPerPerson(ColivingDetails.getRentPerPerson());
                existingColiving.setTotalRooms(ColivingDetails.getTotalRooms());
                existingColiving.setAvailableRooms(ColivingDetails.getAvailableRooms());
                existingColiving.setCleaning(ColivingDetails.getCleaning());

                // Update amenities
                existingColiving.setAmenities(ColivingDetails.getAmenities());

                return ColivingRepository.save(existingColiving);
            });
    }
    public Optional<Coliving> updateColiving(Long id, Coliving ColivingDetails) {
        return ColivingRepository.findById(id)
            .map(existingColiving -> {
                
                existingColiving.setListedDate(ColivingDetails.getListedDate());
                existingColiving.setPrice(ColivingDetails.getPrice());
                existingColiving.setPropertyStatus(ColivingDetails.getPropertyStatus());
                existingColiving.setAddress(ColivingDetails.getAddress());
                existingColiving.setCity(ColivingDetails.getCity());
                existingColiving.setState(ColivingDetails.getState());
                existingColiving.setPin(ColivingDetails.getPin());
                existingColiving.setDescription(ColivingDetails.getDescription());

                existingColiving.setRoomType(ColivingDetails.getRoomType());
                existingColiving.setGenderPreference(ColivingDetails.getGenderPreference());
                existingColiving.setRentPerPerson(ColivingDetails.getRentPerPerson());
                existingColiving.setTotalRooms(ColivingDetails.getTotalRooms());
                existingColiving.setAvailableRooms(ColivingDetails.getAvailableRooms());
                existingColiving.setCleaning(ColivingDetails.getCleaning());
                
                return ColivingRepository.save(existingColiving);
            });
    }
}