package com.realestate.real_estate_management.service;

import com.realestate.real_estate_management.entity.Land;
import com.realestate.real_estate_management.entity.Seller; // <-- Import
import com.realestate.real_estate_management.repository.LandRepository;
import com.realestate.real_estate_management.repository.SellerRepository; // <-- Import
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.security.Principal; // <-- Import

@Service
public class LandService {

    @Autowired
    private LandRepository landRepository;

    // --- NEW DEPENDENCY ---
    @Autowired
    private SellerRepository sellerRepository;
    // --- END NEW DEPENDENCY ---

    // --- UPDATED METHOD ---
    public Land saveLand(Land land, Principal principal) {
        Seller seller = sellerRepository.findByEmail(principal.getName());
        if (seller == null) {
            throw new RuntimeException("Seller profile not found for authenticated user.");
        }
        land.setSeller(seller);
        return landRepository.save(land);
    }
    // --- END UPDATED METHOD ---

    public Optional<Land> updateLand(Long id, Land landDetails) {
        return landRepository.findById(id)
            .map(existingLand -> {
                // ... (all other set... methods remain)
                existingLand.setZoningType(landDetails.getZoningType());

                return landRepository.save(existingLand);
            });
    }
}