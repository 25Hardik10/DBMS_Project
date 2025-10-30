package com.realestate.real_estate_management.controller;

import com.realestate.real_estate_management.entity.Tenant;
import com.realestate.real_estate_management.service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tenants")
public class TenantController {

    @Autowired
    private TenantService tenantService;

    @PostMapping
    public Tenant createTenant(@RequestBody Tenant tenant) {
        return tenantService.saveTenant(tenant);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tenant> updateTenant(@PathVariable Long id, @RequestBody Tenant tenantDetails) {
        
        return tenantService.updateTenant(id, tenantDetails)
                .map(updatedTenant -> ResponseEntity.ok(updatedTenant)) // 200 OK
                .orElse(ResponseEntity.notFound().build()); // 404 Not Found
    }
}