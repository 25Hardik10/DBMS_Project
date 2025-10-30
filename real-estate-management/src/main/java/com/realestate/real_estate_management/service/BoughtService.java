package com.realestate.real_estate_management.service;

import com.realestate.real_estate_management.entity.*;
import com.realestate.real_estate_management.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BoughtService {

    @Autowired private BoughtRepository boughtRepository;
    @Autowired private PropertyRepository propertyRepository;
    @Autowired private BuyerRepository buyerRepository; // Needed for Bought_By
    // SellerRepository no longer needed here as we get Seller via Property entity.

    /**
     * Creates and saves a new Bought transaction.
     * * NOTE: Seller is determined automatically from the Property's owner.
     * @param propertyId The ID of the property being bought.
     * @param buyerEmail The email of the buyer.
     * @param boughtDetails The Bought entity from the request body.
     * @return The saved Bought entity.
     */
    public Bought createBought(Long propertyId, String buyerEmail, Bought boughtDetails) {

        // 1. Fetch entities (Property, Buyer)
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found with id: " + propertyId));

        Buyer buyer = buyerRepository.findByEmail(buyerEmail); 
        if (buyer == null) { throw new RuntimeException("Buyer not found."); }

        // 2. CRITICAL: Get the Seller directly from the Property entity
        Seller seller = property.getSeller();
        if (seller == null) { throw new RuntimeException("Property does not have an assigned Seller."); }


        // 3. Build the complete Bought object
        Bought newBought = new Bought();
        
        // CRITICAL: Instantiate the EmbeddedId
        newBought.setId(new BoughtKey()); 

        // 4. Set the relationships (which populates the BoughtKey via @MapsId)
        newBought.setProperty(property);
        newBought.setBuyer(buyer);
        newBought.setSeller(seller); // Set the retrieved seller
        
        // 5. Set transactional details
        newBought.setAmount(boughtDetails.getAmount());
        newBought.setBuyingDate(LocalDate.now());
        newBought.setModeOfPayment(boughtDetails.getModeOfPayment());

        return boughtRepository.save(newBought);
    }

    public List<Bought> getPurchasesByBuyerEmail(String email) {
        return boughtRepository.findByBuyer_Email(email);
    }
}