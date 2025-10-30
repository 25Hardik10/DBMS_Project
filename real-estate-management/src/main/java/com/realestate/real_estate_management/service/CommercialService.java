package com.realestate.real_estate_management.service;

import com.realestate.real_estate_management.entity.Commercial;
import com.realestate.real_estate_management.entity.Seller; // <-- Import
import com.realestate.real_estate_management.repository.CommercialRepository;
import com.realestate.real_estate_management.repository.SellerRepository; // <-- Import
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.security.Principal; // <-- Import

@Service
public class CommercialService {

    @Autowired
    private CommercialRepository commercialRepository;

    // --- NEW DEPENDENCY ---
    @Autowired
    private SellerRepository sellerRepository;
    // --- END NEW DEPENDENCY ---

    // --- UPDATED METHOD ---
    public Commercial saveCommercial(Commercial commercial, Principal principal) {
        Seller seller = sellerRepository.findByEmail(principal.getName());
        if (seller == null) {
            throw new RuntimeException("Seller profile not found for authenticated user.");
        }
        commercial.setSeller(seller);
        return commercialRepository.save(commercial);
    }
    // --- END UPDATED METHOD ---

    public Optional<Commercial> updateCommercial(Long id, Commercial commercialDetails) {
        return commercialRepository.findById(id)
            .map(existingCommercial -> {
                // ... (all other set... methods remain)
                existingCommercial.setWashrooms(commercialDetails.getWashrooms());

                return commercialRepository.save(existingCommercial);
            });
    }
}