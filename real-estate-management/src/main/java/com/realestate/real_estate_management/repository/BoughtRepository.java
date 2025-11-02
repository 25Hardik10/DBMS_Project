package com.realestate.real_estate_management.repository;

import com.realestate.real_estate_management.entity.Bought;
import com.realestate.real_estate_management.entity.BoughtKey;
import com.realestate.real_estate_management.entity.Buyer;
import com.realestate.real_estate_management.entity.Property;
import com.realestate.real_estate_management.entity.Seller;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List; 

public interface BoughtRepository extends JpaRepository<Bought, BoughtKey> {

    List<Bought> findByBuyer_Email(String email);
    void deleteAllByBuyer(Buyer buyer);
    void deleteAllBySeller(Seller seller);
    void deleteAllByProperty(Property property);
}