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

    @PostMapping("/properties/{propertyId}/bought")
    public ResponseEntity<Bought> createBought(
            @PathVariable Long propertyId,
            @RequestBody Bought bought,
            Principal principal) {
        
        String buyerEmail = principal.getName(); 
        
        Bought savedBought = boughtService.createBought(propertyId, buyerEmail, bought); 
        
        return ResponseEntity.ok(savedBought);
    }
}