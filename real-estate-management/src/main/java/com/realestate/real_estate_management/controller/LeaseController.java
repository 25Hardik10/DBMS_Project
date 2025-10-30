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

    /**
     * POST /api/properties/{propertyId}/lease
     * Records a new lease transaction. (Requires authenticated user as Tenant)
     * @param propertyId The ID of the rental property.
     * @param lease The JSON payload.
     * @param principal The authenticated user (Tenant).
     * @return The created Lease object.
     */
    @PostMapping("/properties/{propertyId}/lease")
    public ResponseEntity<Lease> createLease(
            @PathVariable Long propertyId,
            @RequestBody Lease lease,
            Principal principal) {
        
        // The principal is the Tenant (Borrowed_By)
        String tenantEmail = principal.getName(); 
        
        // Corrected method call: only three arguments are needed now
        Lease savedLease = leaseService.createLease(propertyId, tenantEmail, lease); 
        
        return ResponseEntity.ok(savedLease);
    }
}