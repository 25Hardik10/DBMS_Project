package com.realestate.real_estate_management.controller;

import com.realestate.real_estate_management.entity.Lease;
import com.realestate.real_estate_management.service.LeaseService;
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
public class LeaseController {

    @Autowired
    private LeaseService leaseService;

    @PostMapping("/properties/{propertyId}/lease")
    public ResponseEntity<Lease> createLease(
            @PathVariable Long propertyId,
            @RequestBody Lease lease,
            Principal principal) {
        
        String tenantEmail = principal.getName(); 
        
        Lease savedLease = leaseService.createLease(propertyId, tenantEmail, lease); 
        
        return ResponseEntity.ok(savedLease);
    }
}