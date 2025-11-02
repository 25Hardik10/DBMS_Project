package com.realestate.real_estate_management.repository;

import com.realestate.real_estate_management.entity.Lease;
import com.realestate.real_estate_management.entity.LeaseKey;
import com.realestate.real_estate_management.entity.Property;
import com.realestate.real_estate_management.entity.Seller;
import com.realestate.real_estate_management.entity.Tenant;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List; 

public interface LeaseRepository extends JpaRepository<Lease, LeaseKey> {
    List<Lease> findByTenant_Email(String email);void deleteAllByTenant(Tenant tenant);
    void deleteAllBySeller(Seller seller);
    void deleteAllByProperty(Property property);
}