package com.realestate.real_estate_management.controller;

import com.realestate.real_estate_management.entity.Land;
import com.realestate.real_estate_management.service.LandService;
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
@RequestMapping("/api/lands")
public class LandController {

    @Autowired
    private LandService landService;

    @PostMapping
    public Land createLand(@RequestBody Land land, Principal principal) {
        land.setPropertyType("Land");
        return landService.saveLand(land, principal);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Land> updateLand(
            @PathVariable Long id, 
            @RequestBody Land landDetails,
            Principal principal) {
        
        try {
            return landService.updateLand(id, landDetails, principal.getName())
                    .map(updatedLand -> ResponseEntity.ok(updatedLand)) 
                    .orElse(ResponseEntity.notFound().build()); 
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}