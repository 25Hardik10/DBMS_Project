package com.realestate.real_estate_management.repository;

import com.realestate.real_estate_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    
}