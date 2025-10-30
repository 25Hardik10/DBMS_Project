package com.realestate.real_estate_management.repository;

import com.realestate.real_estate_management.entity.Commercial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommercialRepository extends JpaRepository<Commercial, Long> {

}