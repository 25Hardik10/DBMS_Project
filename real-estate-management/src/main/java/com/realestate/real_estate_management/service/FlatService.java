package com.realestate.real_estate_management.service;

import com.realestate.real_estate_management.entity.Flat;
import com.realestate.real_estate_management.entity.Seller; // <-- Import Seller
import com.realestate.real_estate_management.repository.FlatRepository;
import com.realestate.real_estate_management.repository.SellerRepository; // <-- Import SellerRepository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.security.Principal; // <-- Import Principal

@Service
public class FlatService {

    @Autowired
    private FlatRepository flatRepository;

    // --- NEW DEPENDENCY ---
    @Autowired
    private SellerRepository sellerRepository;
    // --- END NEW DEPENDENCY ---

    // --- UPDATED METHOD ---
    public Flat saveFlat(Flat flat, Principal principal) {
        // Find the logged-in seller by their email (which is the principal's name)
        Seller seller = sellerRepository.findByEmail(principal.getName());
        if (seller == null) {
            throw new RuntimeException("Seller profile not found for authenticated user.");
        }
        
        // Set the authenticated seller as the owner
        flat.setSeller(seller);
        
        return flatRepository.save(flat);
    }
    // --- END UPDATED METHOD ---
    
    public Optional<Flat> updateFlat(Long id, Flat flatDetails) {
        return flatRepository.findById(id)
            .map(existingFlat -> {
                existingFlat.setPrice(flatDetails.getPrice());
                existingFlat.setPropertyStatus(flatDetails.getPropertyStatus());
                // ... (all other set... methods remain)
                existingFlat.setCarpetArea(flatDetails.getCarpetArea());

                return flatRepository.save(existingFlat);
            });
    }
}