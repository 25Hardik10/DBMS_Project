package com.realestate.real_estate_management.controller;

import com.realestate.real_estate_management.entity.Bought; 
import com.realestate.real_estate_management.entity.Buyer;
import com.realestate.real_estate_management.service.BoughtService; 
import com.realestate.real_estate_management.service.BuyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/buyers")
public class BuyerController {

    @Autowired
    private BuyerService buyerService;

    @Autowired
    private BoughtService boughtService;
    
    @PostMapping
    public Buyer createBuyer(@RequestBody Buyer buyer) {
        return buyerService.saveBuyer(buyer);
    }

    @GetMapping("/my-purchases")
    public ResponseEntity<List<Bought>> getMyPurchases(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(401).build();
        }
        List<Bought> purchases = boughtService.getPurchasesByBuyerEmail(principal.getName());
        return ResponseEntity.ok(purchases);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Buyer> updateBuyer(@PathVariable Long id, @RequestBody Buyer buyerDetails) {

        return buyerService.updateBuyer(id, buyerDetails)
                .map(updatedBuyer -> ResponseEntity.ok(updatedBuyer)) 
                .orElse(ResponseEntity.notFound().build()); 
    }
}