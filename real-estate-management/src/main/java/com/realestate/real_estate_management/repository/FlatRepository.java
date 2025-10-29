package com.realestate.real_estate_management.repository;

import com.realestate.real_estate_management.entity.Flat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlatRepository extends JpaRepository<Flat, Long> {
    
}