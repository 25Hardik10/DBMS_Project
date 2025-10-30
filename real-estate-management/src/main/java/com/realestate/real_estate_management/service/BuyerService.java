package com.realestate.real_estate_management.service;

import com.realestate.real_estate_management.entity.Buyer;
import com.realestate.real_estate_management.repository.BuyerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional; 

@Service
public class BuyerService {

    @Autowired
    private BuyerRepository buyerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Buyer saveBuyer(Buyer buyer) {
        buyer.setPassword(passwordEncoder.encode(buyer.getPassword()));
        return buyerRepository.save(buyer);
    }

    public Optional<Buyer> updateBuyer(Long id, Buyer buyerDetails) {
        
        return buyerRepository.findById(id)
            .map(existingBuyer -> {
                
                existingBuyer.setFirstName(buyerDetails.getFirstName());
                existingBuyer.setMiddleName(buyerDetails.getMiddleName());
                existingBuyer.setLastName(buyerDetails.getLastName());
                existingBuyer.setEmail(buyerDetails.getEmail());
                existingBuyer.setMobile(buyerDetails.getMobile());
                
                if (buyerDetails.getPassword() != null && !buyerDetails.getPassword().isEmpty()) {
                    existingBuyer.setPassword(passwordEncoder.encode(buyerDetails.getPassword()));
                }

                existingBuyer.setBudget(buyerDetails.getBudget());
                existingBuyer.setPreferredLocation(buyerDetails.getPreferredLocation());

                return buyerRepository.save(existingBuyer);
            });
    }
}