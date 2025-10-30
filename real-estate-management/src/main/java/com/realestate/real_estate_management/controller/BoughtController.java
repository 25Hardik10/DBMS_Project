package com.realestate.real_estate_management.controller;

import com.realestate.real_estate_management.entity.Bought;
import com.realestate.real_estate_management.service.BoughtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;

@RestController
@RequestMapping("/api")
public class BoughtController {

    @Autowired
    private BoughtService boughtService;

    /**
     * POST /api/properties/{propertyId}/bought
     * Records a new property purchase transaction. (Requires authenticated user as Buyer)
     * @param propertyId The ID of the property that was bought.
     * @param bought The JSON payload (amount, mode_of_payment).
     * @param principal The authenticated user (Buyer).
     * @return The created Bought object.
     */
    @PostMapping("/properties/{propertyId}/bought")
    public ResponseEntity<Bought> createBought(
            @PathVariable Long propertyId,
            @RequestBody Bought bought,
            Principal principal) {
        
        // The principal is the Buyer (Bought_By)
        String buyerEmail = principal.getName(); 
        
        // Corrected method call: only three arguments are needed now
        Bought savedBought = boughtService.createBought(propertyId, buyerEmail, bought); 
        
        return ResponseEntity.ok(savedBought);
    }
}