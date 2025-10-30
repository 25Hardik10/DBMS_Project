package com.realestate.real_estate_management.controller;

import com.realestate.real_estate_management.entity.Rental;
import com.realestate.real_estate_management.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import java.security.Principal; // <-- Import Principal

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    @Autowired
    private RentalService RentalService;

    // --- UPDATED METHOD ---
    @PostMapping
    public Rental createRental(@RequestBody Rental rental, Principal principal) {
        rental.setPropertyType("Rental");
        return RentalService.saveRental(rental, principal);
    }
    // --- END UPDATED METHOD ---

    @PutMapping("/{id}")
    public ResponseEntity<Rental> updateRental(@PathVariable Long id, @RequestBody Rental RentalDetails) {
        return RentalService.updateRental(id, RentalDetails)
                .map(updatedRental -> ResponseEntity.ok(updatedRental)) // 200 OK
                .orElse(ResponseEntity.notFound().build()); // 404 Not Found
    }
}