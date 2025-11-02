package com.realestate.real_estate_management.service;

import com.realestate.real_estate_management.entity.*;
import com.realestate.real_estate_management.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects; // --- FIX: Import Objects

@Service
@Transactional
public class BoughtService {

    @Autowired private BoughtRepository boughtRepository;
    @Autowired private PropertyRepository propertyRepository;
    @Autowired private BuyerRepository buyerRepository; 
   
    public Bought createBought(Long propertyId, String buyerEmail, Bought boughtDetails) {

        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found with id: " + propertyId));

        // --- FIX 1: Check if the property is available for sale ---
        // We use !Objects.equals to safely handle nulls
        if (!Objects.equals(property.getPropertyStatus(), "FOR_SALE")) {
            throw new RuntimeException("Property with id: " + propertyId + " is not available for sale.");
        }

        Buyer buyer = buyerRepository.findByEmail(buyerEmail); 
        if (buyer == null) { throw new RuntimeException("Buyer not found."); }

        Seller seller = property.getSeller();
        if (seller == null) { throw new RuntimeException("Property does not have an assigned Seller."); }

        // --- FIX 2: Update the property's status ---
        property.setPropertyStatus("SOLD");
        propertyRepository.save(property); // This is transactional, so it's safe

        Bought newBought = new Bought();
        
        newBought.setId(new BoughtKey()); 

        newBought.setProperty(property);
        newBought.setBuyer(buyer);
        newBought.setSeller(seller);
        
        newBought.setAmount(boughtDetails.getAmount());
        newBought.setBuyingDate(LocalDate.now());
        newBought.setModeOfPayment(boughtDetails.getModeOfPayment());

        return boughtRepository.save(newBought);
    }

    @Transactional(readOnly = true)
    public List<Bought> getPurchasesByBuyerEmail(String email) {
        return boughtRepository.findByBuyer_Email(email);
    }
}