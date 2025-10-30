package com.realestate.real_estate_management.repository;

import com.realestate.real_estate_management.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental, Long> {

}
