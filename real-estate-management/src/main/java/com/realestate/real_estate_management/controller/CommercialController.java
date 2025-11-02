package com.realestate.real_estate_management.controller;

import com.realestate.real_estate_management.entity.Commercial;
import com.realestate.real_estate_management.service.CommercialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal; 

@RestController
@RequestMapping("/api/commercials")
public class CommercialController {

    @Autowired
    private CommercialService commercialService;

    @PostMapping
    public Commercial createCommercial(@RequestBody Commercial commercial, Principal principal) {
        commercial.setPropertyType("Commercial");;
        return commercialService.saveCommercial(commercial, principal);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Commercial> updateCommercial(
            @PathVariable Long id, 
            @RequestBody Commercial commercialDetails,
            Principal principal) {
        
        try {
            return commercialService.updateCommercial(id, commercialDetails, principal.getName())
                    .map(updatedCommercial -> ResponseEntity.ok(updatedCommercial))
                    .orElse(ResponseEntity.notFound().build()); 
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}