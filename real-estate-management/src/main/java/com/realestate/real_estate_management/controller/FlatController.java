package com.realestate.real_estate_management.controller;

import com.realestate.real_estate_management.entity.Flat;
import com.realestate.real_estate_management.service.FlatService;
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
@RequestMapping("/api/flats")
public class FlatController {

    @Autowired
    private FlatService flatService;

    @PostMapping
    public Flat createFlat(@RequestBody Flat flat, Principal principal) {
        flat.setPropertyType("Flat");
        return flatService.saveFlat(flat, principal);
    }
@PutMapping("/{id}")
    public ResponseEntity<Flat> updateFlat(
            @PathVariable Long id, 
            @RequestBody Flat flatDetails,
            Principal principal) {
        
        try {
            return flatService.updateFlat(id, flatDetails, principal.getName())
                    .map(updatedFlat -> ResponseEntity.ok(updatedFlat)) 
                    .orElse(ResponseEntity.notFound().build()); 
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}