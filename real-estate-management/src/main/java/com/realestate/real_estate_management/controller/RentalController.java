package com.realestate.real_estate_management.controller;

import com.realestate.real_estate_management.entity.Rental;
import com.realestate.real_estate_management.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import java.security.Principal; 

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    @Autowired
    private RentalService RentalService;

    @PostMapping
    public Rental createRental(@RequestBody Rental rental, Principal principal) {
        rental.setPropertyType("Rental");
        return RentalService.saveRental(rental, principal);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Rental> updateRental(
            @PathVariable Long id, 
            @RequestBody Rental RentalDetails,
            Principal principal) {
        
        try {
            return RentalService.updateRental(id, RentalDetails, principal.getName())
                    .map(updatedRental -> ResponseEntity.ok(updatedRental))
                    .orElse(ResponseEntity.notFound().build());
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}