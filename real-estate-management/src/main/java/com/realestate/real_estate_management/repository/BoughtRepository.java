package com.realestate.real_estate_management.repository;

import com.realestate.real_estate_management.entity.Bought;
import com.realestate.real_estate_management.entity.BoughtKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoughtRepository extends JpaRepository<Bought, BoughtKey> {

}