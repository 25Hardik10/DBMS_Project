package com.realestate.real_estate_management.controller;

import com.realestate.real_estate_management.entity.Buyer;
import com.realestate.real_estate_management.service.BuyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.PathVariable; 
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/buyers")
public class BuyerController {

    @Autowired
    private BuyerService buyerService;

    @PostMapping
    public Buyer createBuyer(@RequestBody Buyer buyer) {
        return buyerService.saveBuyer(buyer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Buyer> updateBuyer(@PathVariable Long id, @RequestBody Buyer buyerDetails) {
        
        return buyerService.updateBuyer(id, buyerDetails)
                .map(updatedBuyer -> ResponseEntity.ok(updatedBuyer)) // 200 OK
                .orElse(ResponseEntity.notFound().build()); // 404 Not Found
    }
}