package com.realestate.real_estate_management.repository;

import com.realestate.real_estate_management.entity.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyerRepository extends JpaRepository<Buyer, Long> {
    
}