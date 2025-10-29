package com.realestate.real_estate_management.repository;

import com.realestate.real_estate_management.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Long> {
    
}