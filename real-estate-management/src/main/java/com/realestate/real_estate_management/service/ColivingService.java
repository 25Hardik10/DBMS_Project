package com.realestate.real_estate_management.service;

import com.realestate.real_estate_management.entity.Coliving;
import com.realestate.real_estate_management.entity.Seller; // <-- Import
import com.realestate.real_estate_management.repository.ColivingRepository;
import com.realestate.real_estate_management.repository.SellerRepository; // <-- Import
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.security.Principal; // <-- Import

@Service
public class ColivingService {

    @Autowired
    private ColivingRepository ColivingRepository;

    // --- NEW DEPENDENCY ---
    @Autowired
    private SellerRepository sellerRepository;
    // --- END NEW DEPENDENCY ---
    
    // --- UPDATED METHOD ---
    public Coliving saveColiving(Coliving coliving, Principal principal) {
        Seller seller = sellerRepository.findByEmail(principal.getName());
        if (seller == null) {
            throw new RuntimeException("Seller profile not found for authenticated user.");
        }
        coliving.setSeller(seller);
        return ColivingRepository.save(coliving);
    }
    // --- END UPDATED METHOD ---
    
    public Optional<Coliving> updateColiving(Long id, Coliving ColivingDetails) {
        return ColivingRepository.findById(id)
            .map(existingColiving -> {
                // ... (all other set... methods remain)
                existingColiving.setCleaning(ColivingDetails.getCleaning());

                return ColivingRepository.save(existingColiving);
            });
    }
}