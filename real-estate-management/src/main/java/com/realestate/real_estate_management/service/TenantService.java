package com.realestate.real_estate_management.service;

import com.realestate.real_estate_management.entity.Tenant;
import com.realestate.real_estate_management.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder; 
import org.springframework.stereotype.Service;
import java.util.Optional; 

@Service
public class TenantService {

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Tenant saveTenant(Tenant tenant) {
        tenant.setPassword(passwordEncoder.encode(tenant.getPassword()));
        return tenantRepository.save(tenant);
    }

    public Optional<Tenant> updateTenant(Long id, Tenant tenantDetails) {
        
        return tenantRepository.findById(id)
            .map(existingTenant -> {
                
                existingTenant.setFirstName(tenantDetails.getFirstName());
                existingTenant.setMiddleName(tenantDetails.getMiddleName());
                existingTenant.setLastName(tenantDetails.getLastName());
                existingTenant.setEmail(tenantDetails.getEmail());
                existingTenant.setMobile(tenantDetails.getMobile());
                
                if (tenantDetails.getPassword() != null && !tenantDetails.getPassword().isEmpty()) {
                    existingTenant.setPassword(passwordEncoder.encode(tenantDetails.getPassword()));
                }

                existingTenant.setBudget(tenantDetails.getBudget());
                existingTenant.setLeaseTerm(tenantDetails.getLeaseTerm());
                existingTenant.setPreferredDate(tenantDetails.getPreferredDate());
                existingTenant.setEmploymentStatus(tenantDetails.getEmploymentStatus());

                return tenantRepository.save(existingTenant);
            });
    }
}