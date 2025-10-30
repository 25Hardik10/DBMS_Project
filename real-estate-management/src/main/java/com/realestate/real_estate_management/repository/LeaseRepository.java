package com.realestate.real_estate_management.repository;

import com.realestate.real_estate_management.entity.Lease;
import com.realestate.real_estate_management.entity.LeaseKey;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List; // <-- Add this import

public interface LeaseRepository extends JpaRepository<Lease, LeaseKey> {

    // --- NEW METHOD ---
    /**
     * Finds all lease records for a tenant, identified by their email.
     * @param email The tenant's email.
     * @return A list of their lease transactions.
     */
    List<Lease> findByTenant_Email(String email);
    // --- END NEW METHOD ---
}