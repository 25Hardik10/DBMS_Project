package com.realestate.real_estate_management.controller;

import com.realestate.real_estate_management.dto.FlatRequestDTO;
import com.realestate.real_estate_management.entity.Flat;
import com.realestate.real_estate_management.entity.Image;
import com.realestate.real_estate_management.service.FlatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/flats")
public class FlatController {

    @Autowired
    private FlatService flatService;

    // ✅ Create Flat (with image URLs support)
    @PostMapping
    public ResponseEntity<?> createFlat(@RequestBody FlatRequestDTO flatRequest, Principal principal) {
        try {
            Flat flat = flatRequest.toEntity();
            flat.setPropertyType("Flat");
            Flat savedFlat = flatService.saveFlat(flat, principal);
            return ResponseEntity.ok(savedFlat);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Failed to create flat: " + e.getMessage());
        }
    }
    // ✅ Update Flat (with ownership check)
    @PutMapping("/{id}")
    public ResponseEntity<Flat> updateFlat(
            @PathVariable Long id,
            @RequestBody Flat flatDetails,
            Principal principal) {

        try {
            return flatService.updateFlat(id, flatDetails, principal.getName())
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());

        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
