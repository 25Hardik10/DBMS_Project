package com.realestate.real_estate_management.controller;

import com.realestate.real_estate_management.entity.Lease; 
import com.realestate.real_estate_management.entity.Tenant;
import com.realestate.real_estate_management.service.LeaseService;
import com.realestate.real_estate_management.service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.*;
import java.security.Principal; 
import java.util.List;

@RestController
@RequestMapping("/api/tenants")
public class TenantController {

    @Autowired
    private TenantService tenantService;

    @Autowired
    private LeaseService leaseService;

    @PostMapping
    public Tenant createTenant(@RequestBody Tenant tenant) {
        return tenantService.saveTenant(tenant);
    }

    @GetMapping("/my-leases")
    public ResponseEntity<List<Lease>> getMyLeases(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(401).build();
        }
        List<Lease> leases = leaseService.getLeasesByTenantEmail(principal.getName());
        return ResponseEntity.ok(leases);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tenant> updateTenant(@PathVariable Long id, @RequestBody Tenant tenantDetails) {

        return tenantService.updateTenant(id, tenantDetails)
                .map(updatedTenant -> ResponseEntity.ok(updatedTenant)) 
                .orElse(ResponseEntity.notFound().build()); 
    }
}