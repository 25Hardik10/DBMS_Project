package com.realestate.real_estate_management.controller;

import com.realestate.real_estate_management.entity.Property; // <-- Import
import com.realestate.real_estate_management.entity.Seller;
import com.realestate.real_estate_management.service.PropertyService; // <-- Import
import com.realestate.real_estate_management.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.*; // <-- Import *
import java.security.Principal; // <-- Import
import java.util.List; // <-- Import

@RestController
@RequestMapping("/api/sellers")
public class SellerController {

    @Autowired
    private SellerService sellerService;

    // --- NEW DEPENDENCY ---
    @Autowired
    private PropertyService propertyService;
    // --- END NEW DEPENDENCY ---

    @PostMapping
    public Seller createSeller(@RequestBody Seller seller) {
        return sellerService.saveSeller(seller);
    }

    // --- NEW ENDPOINT ---
    /**
     * GET /api/sellers/my-properties
     * Gets all properties listed by the currently authenticated seller.
     * @param principal The logged-in user.
     * @return A list of their properties.
     */
    @GetMapping("/my-properties")
    public ResponseEntity<List<Property>> getMyProperties(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(401).build();
        }
        List<Property> properties = propertyService.getPropertiesBySellerEmail(principal.getName());
        return ResponseEntity.ok(properties);
    }
    // --- END NEW ENDPOINT ---

    @PutMapping("/{id}")
    public ResponseEntity<Seller> updateSeller(@PathVariable Long id, @RequestBody Seller sellerDetails) {
        // ... (this method remains unchanged)
        return sellerService.updateSeller(id, sellerDetails)
                .map(updatedSeller -> ResponseEntity.ok(updatedSeller)) 
                .orElse(ResponseEntity.notFound().build()); 
    }
}