package com.realestate.real_estate_management.service;

import com.realestate.real_estate_management.entity.*;
import com.realestate.real_estate_management.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LeaseService {

    @Autowired private LeaseRepository leaseRepository;
    @Autowired private PropertyRepository propertyRepository;
    @Autowired private TenantRepository tenantRepository;
    // We no longer need to Autowire SellerRepository here
    // as we get the seller via the Property entity.

    /**
     * Creates and saves a new Lease transaction.
     * * NOTE: Seller is determined automatically from the Property's owner.
     * * @param propertyId The ID of the property being leased.
     * @param tenantEmail The email of the borrower (Tenant).
     * @param leaseDetails The Lease entity from the request body.
     * @return The saved Lease entity.
     */
    public Lease createLease(Long propertyId, String tenantEmail, Lease leaseDetails) {

        // 1. Fetch entities (Property, Tenant)
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found with id: " + propertyId));

        Tenant tenant = tenantRepository.findByEmail(tenantEmail); 
        if (tenant == null) { throw new RuntimeException("Tenant not found."); }

        // 2. CRITICAL: Get the Seller directly from the Property entity
        Seller seller = property.getSeller();
        if (seller == null) { throw new RuntimeException("Property does not have an assigned Seller."); }


        // 3. Build the complete Lease object
        Lease newLease = new Lease();
        
        // CRITICAL: Instantiate the EmbeddedId
        newLease.setId(new LeaseKey()); 

        // 4. Set the relationships (which populates the LeaseKey via @MapsId)
        newLease.setProperty(property);
        newLease.setTenant(tenant);
        newLease.setSeller(seller); // Set the retrieved seller
        
        // 5. Set transactional details
        newLease.setAmount(leaseDetails.getAmount());
        newLease.setStartDate(LocalDate.now());
        newLease.setEndDate(leaseDetails.getEndDate());
        newLease.setModeOfPayment(leaseDetails.getModeOfPayment());
        newLease.setRemarks(leaseDetails.getRemarks());

        return leaseRepository.save(newLease);
    }

    public List<Lease> getLeasesByTenantEmail(String email) {
        return leaseRepository.findByTenant_Email(email);
    }
}