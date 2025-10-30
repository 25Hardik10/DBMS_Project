package com.realestate.real_estate_management.service;

import com.realestate.real_estate_management.entity.Rental;
import com.realestate.real_estate_management.entity.Seller; // <-- Import
import com.realestate.real_estate_management.repository.RentalRepository;
import com.realestate.real_estate_management.repository.SellerRepository; // <-- Import
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.security.Principal; // <-- Import

@Service
public class RentalService {

    @Autowired
    private RentalRepository RentalRepository;

    // --- NEW DEPENDENCY ---
    @Autowired
    private SellerRepository sellerRepository;
    // --- END NEW DEPENDENCY ---

    // --- UPDATED METHOD ---
    public Rental saveRental(Rental rental, Principal principal) {
        Seller seller = sellerRepository.findByEmail(principal.getName());
        if (seller == null) {
            throw new RuntimeException("Seller profile not found for authenticated user.");
        }
        rental.setSeller(seller);
        return RentalRepository.save(rental);
    }
    // --- END UPDATED METHOD ---
    
    public Optional<Rental> updateRental(Long id, Rental RentalDetails) {
        return RentalRepository.findById(id)
            .map(existingRental -> {
                // ... (all other set... methods remain)
                existingRental.setTenantType(RentalDetails.getTenantType());

                return RentalRepository.save(existingRental);
            });
    }
}