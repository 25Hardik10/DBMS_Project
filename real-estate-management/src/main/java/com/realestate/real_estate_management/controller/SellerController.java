package com.realestate.real_estate_management.controller;

import com.realestate.real_estate_management.entity.Property; 
import com.realestate.real_estate_management.entity.Seller;
import com.realestate.real_estate_management.service.PropertyService;
import com.realestate.real_estate_management.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.*; 
import java.security.Principal;
import java.util.List;
@RestController
@RequestMapping("/api/sellers")
public class SellerController {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private PropertyService propertyService;

    @PostMapping
    public Seller createSeller(@RequestBody Seller seller) {
        return sellerService.saveSeller(seller);
    }

    @GetMapping("/my-properties")
    public ResponseEntity<List<Property>> getMyProperties(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(401).build();
        }
        List<Property> properties = propertyService.getPropertiesBySellerEmail(principal.getName());
        return ResponseEntity.ok(properties);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Seller> updateSeller(@PathVariable Long id, @RequestBody Seller sellerDetails) {
        return sellerService.updateSeller(id, sellerDetails)
                .map(updatedSeller -> ResponseEntity.ok(updatedSeller)) 
                .orElse(ResponseEntity.notFound().build()); 
    }
}