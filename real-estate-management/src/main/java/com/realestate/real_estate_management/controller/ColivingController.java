package com.realestate.real_estate_management.controller;

import com.realestate.real_estate_management.entity.Coliving;
import com.realestate.real_estate_management.service.ColivingService;
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
@RequestMapping("/api/colivings")
public class ColivingController {

    @Autowired
    private ColivingService ColivingService;

    // --- UPDATED METHOD ---
    @PostMapping
    public Coliving createColiving(@RequestBody Coliving coliving, Principal principal) {
        coliving.setPropertyType("Coliving");
        return ColivingService.saveColiving(coliving, principal);
    }
    // --- END UPDATED METHOD ---

    @PutMapping("/{id}")
    public ResponseEntity<Coliving> updateColiving(@PathVariable Long id, @RequestBody Coliving ColivingDetails) {
        return ColivingService.updateColiving(id, ColivingDetails)
                .map(updatedColiving -> ResponseEntity.ok(updatedColiving)) // 200 OK
                .orElse(ResponseEntity.notFound().build()); // 404 Not Found
    }
}