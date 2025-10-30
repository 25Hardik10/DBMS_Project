package com.realestate.real_estate_management.repository;

import com.realestate.real_estate_management.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantRepository extends JpaRepository<Tenant, Long> {
    
    // Allows service layer to find the Tenant by their unique email (username)
    Tenant findByEmail(String email);
}