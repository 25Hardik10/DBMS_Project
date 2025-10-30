package com.realestate.real_estate_management.controller;

import com.realestate.real_estate_management.entity.Bought; // <-- Import
import com.realestate.real_estate_management.entity.Buyer;
import com.realestate.real_estate_management.service.BoughtService; // <-- Import
import com.realestate.real_estate_management.service.BuyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.*; // <-- Import *
import java.security.Principal; // <-- Import
import java.util.List; // <-- Import

@RestController
@RequestMapping("/api/buyers")
public class BuyerController {

    @Autowired
    private BuyerService buyerService;

    // --- NEW DEPENDENCY ---
    @Autowired
    private BoughtService boughtService;
    // --- END NEW DEPENDENCY ---

    @PostMapping
    public Buyer createBuyer(@RequestBody Buyer buyer) {
        return buyerService.saveBuyer(buyer);
    }

    // --- NEW ENDPOINT ---
    /**
     * GET /api/buyers/my-purchases
     * Gets all purchases made by the currently authenticated buyer.
     * @param principal The logged-in user.
     * @return A list of their purchase transactions.
     */
    @GetMapping("/my-purchases")
    public ResponseEntity<List<Bought>> getMyPurchases(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(401).build();
        }
        List<Bought> purchases = boughtService.getPurchasesByBuyerEmail(principal.getName());
        return ResponseEntity.ok(purchases);
    }
    // --- END NEW ENDPOINT ---

    @PutMapping("/{id}")
    public ResponseEntity<Buyer> updateBuyer(@PathVariable Long id, @RequestBody Buyer buyerDetails) {
        // ... (this method remains unchanged)
        return buyerService.updateBuyer(id, buyerDetails)
                .map(updatedBuyer -> ResponseEntity.ok(updatedBuyer)) 
                .orElse(ResponseEntity.notFound().build()); 
    }
}