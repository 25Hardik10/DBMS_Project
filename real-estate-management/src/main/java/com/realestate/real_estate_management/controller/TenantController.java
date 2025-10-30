package com.realestate.real_estate_management.controller;

import com.realestate.real_estate_management.entity.Lease; // <-- Import
import com.realestate.real_estate_management.entity.Tenant;
import com.realestate.real_estate_management.service.LeaseService; // <-- Import
import com.realestate.real_estate_management.service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.*; // <-- Import *
import java.security.Principal; // <-- Import
import java.util.List; // <-- Import

@RestController
@RequestMapping("/api/tenants")
public class TenantController {

    @Autowired
    private TenantService tenantService;

    // --- NEW DEPENDENCY ---
    @Autowired
    private LeaseService leaseService;
    // --- END NEW DEPENDENCY ---

    @PostMapping
    public Tenant createTenant(@RequestBody Tenant tenant) {
        return tenantService.saveTenant(tenant);
    }

    // --- NEW ENDPOINT ---
    /**
     * GET /api/tenants/my-leases
     * Gets all leases for the currently authenticated tenant.
     * @param principal The logged-in user.
     * @return A list of their lease transactions.
     */
    @GetMapping("/my-leases")
    public ResponseEntity<List<Lease>> getMyLeases(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(401).build();
        }
        List<Lease> leases = leaseService.getLeasesByTenantEmail(principal.getName());
        return ResponseEntity.ok(leases);
    }
    // --- END NEW ENDPOINT ---

    @PutMapping("/{id}")
    public ResponseEntity<Tenant> updateTenant(@PathVariable Long id, @RequestBody Tenant tenantDetails) {
        // ... (this method remains unchanged)
        return tenantService.updateTenant(id, tenantDetails)
                .map(updatedTenant -> ResponseEntity.ok(updatedTenant)) 
                .orElse(ResponseEntity.notFound().build()); 
    }
}