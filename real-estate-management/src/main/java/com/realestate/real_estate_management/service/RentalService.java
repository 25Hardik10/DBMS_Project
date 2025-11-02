package com.realestate.real_estate_management.service;

import com.realestate.real_estate_management.entity.Rental;
import com.realestate.real_estate_management.entity.Seller; 
import com.realestate.real_estate_management.repository.RentalRepository;
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
public class RentalService {

    @Autowired
    private RentalRepository RentalRepository;

    @Autowired
    private SellerRepository sellerRepository;

    public Rental saveRental(Rental rental, Principal principal) {
        Seller seller = sellerRepository.findByEmail(principal.getName());
        if (seller == null) {
            throw new RuntimeException("Seller profile not found for authenticated user.");
        }
        rental.setSeller(seller);
        return RentalRepository.save(rental);
    }
    public Optional<Rental> updateRental(Long id, Rental RentalDetails, String sellerEmail) {
        return RentalRepository.findById(id)
            .map(existingRental -> {

                // 1. CHECK OWNERSHIP
                if (!Objects.equals(existingRental.getSeller().getEmail(), sellerEmail)) {
                    throw new AccessDeniedException("You do not have permission to update this property.");
                }

                // 2. UPDATE ALL FIELDS
                // Base Property fields
                existingRental.setListedDate(RentalDetails.getListedDate());
                existingRental.setPrice(RentalDetails.getPrice());
                existingRental.setPropertyStatus(RentalDetails.getPropertyStatus());
                existingRental.setAddress(RentalDetails.getAddress());
                existingRental.setCity(RentalDetails.getCity());
                existingRental.setState(RentalDetails.getState());
                existingRental.setPin(RentalDetails.getPin());
                existingRental.setDescription(RentalDetails.getDescription());
                
                // Rental specific fields
                existingRental.setRent(RentalDetails.getRent());
                existingRental.setLeaseTerm(RentalDetails.getLeaseTerm());
                existingRental.setFurnishingStatus(RentalDetails.getFurnishingStatus());
                existingRental.setPetAllowed(RentalDetails.getPetAllowed());
                existingRental.setDeposit(RentalDetails.getDeposit());
                existingRental.setAvailableFrom(RentalDetails.getAvailableFrom());
                existingRental.setTenantType(RentalDetails.getTenantType());

                // Update amenities
                existingRental.setAmenities(RentalDetails.getAmenities());

                return RentalRepository.save(existingRental);
            });
    }
    public Optional<Rental> updateRental(Long id, Rental RentalDetails) {
        return RentalRepository.findById(id)
            .map(existingRental -> {
                existingRental.setListedDate(RentalDetails.getListedDate());
                existingRental.setPrice(RentalDetails.getPrice());
                existingRental.setPropertyStatus(RentalDetails.getPropertyStatus());
                existingRental.setAddress(RentalDetails.getAddress());
                existingRental.setCity(RentalDetails.getCity());
                existingRental.setState(RentalDetails.getState());
                existingRental.setPin(RentalDetails.getPin());
                existingRental.setDescription(RentalDetails.getDescription());

                existingRental.setRent(RentalDetails.getRent());
                existingRental.setLeaseTerm(RentalDetails.getLeaseTerm());
                existingRental.setFurnishingStatus(RentalDetails.getFurnishingStatus());
                existingRental.setPetAllowed(RentalDetails.getPetAllowed());
                existingRental.setDeposit(RentalDetails.getDeposit());
                existingRental.setAvailableFrom(RentalDetails.getAvailableFrom());
                existingRental.setTenantType(RentalDetails.getTenantType());

                return RentalRepository.save(existingRental);
            });
    }
}