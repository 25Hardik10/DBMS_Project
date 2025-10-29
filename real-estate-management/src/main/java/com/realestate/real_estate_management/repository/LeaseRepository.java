package com.realestate.real_estate_management.repository;

import com.realestate.real_estate_management.entity.Lease;
import com.realestate.real_estate_management.entity.LeaseKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaseRepository extends JpaRepository<Lease, LeaseKey> {

}