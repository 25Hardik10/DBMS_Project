package com.realestate.real_estate_management.repository;

import com.realestate.real_estate_management.entity.Bought;
import com.realestate.real_estate_management.entity.BoughtKey;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List; // <-- Add this import

public interface BoughtRepository extends JpaRepository<Bought, BoughtKey> {

    // --- NEW METHOD ---
    /**
     * Finds all purchase records for a buyer, identified by their email.
     * @param email The buyer's email.
     * @return A list of their purchase transactions.
     */
    List<Bought> findByBuyer_Email(String email);
    // --- END NEW METHOD ---
}