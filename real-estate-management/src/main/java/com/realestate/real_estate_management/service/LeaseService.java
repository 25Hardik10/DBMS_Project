package com.realestate.real_estate_management.service;

import com.realestate.real_estate_management.entity.*;
import com.realestate.real_estate_management.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Make sure this is imported

import java.time.LocalDate;
import java.util.List;
import java.util.Objects; // --- FIX: Import Objects

@Service
@Transactional // Make sure this is present
public class LeaseService {

    @Autowired private LeaseRepository leaseRepository;
    @Autowired private PropertyRepository propertyRepository;
    @Autowired private TenantRepository tenantRepository;

    public Lease createLease(Long propertyId, String tenantEmail, Lease leaseDetails) {

        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found with id: " + propertyId));

        // --- FIX 1: Check if the property is available for rent ---
        if (!Objects.equals(property.getPropertyStatus(), "FOR_RENT")) {
            throw new RuntimeException("Property with id: " + propertyId + " is not available for rent.");
        }

        Tenant tenant = tenantRepository.findByEmail(tenantEmail); 
        if (tenant == null) { throw new RuntimeException("Tenant not found."); }

        Seller seller = property.getSeller();
        if (seller == null) { throw new RuntimeException("Property does not have an assigned Seller."); }

        // --- FIX 2: Update the property's status ---//
        property.setPropertyStatus("LEASED");
        propertyRepository.save(property); // This is safe within the transaction

        Lease newLease = new Lease();
        
        newLease.setId(new LeaseKey()); 

        newLease.setProperty(property);
        newLease.setTenant(tenant);
        newLease.setSeller(seller); 
        newLease.setAmount(leaseDetails.getAmount());
        newLease.setStartDate(LocalDate.now());
        newLease.setEndDate(leaseDetails.getEndDate());
        newLease.setModeOfPayment(leaseDetails.getModeOfPayment());
        newLease.setRemarks(leaseDetails.getRemarks());

        return leaseRepository.save(newLease);
    }

    @Transactional(readOnly = true)
    public List<Lease> getLeasesByTenantEmail(String email) {
        return leaseRepository.findByTenant_Email(email);
    }
}