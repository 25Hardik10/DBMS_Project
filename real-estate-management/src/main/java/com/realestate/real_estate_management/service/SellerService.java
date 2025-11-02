package com.realestate.real_estate_management.service;

import com.realestate.real_estate_management.entity.Seller;
import com.realestate.real_estate_management.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder; 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional; 

@Service
@Transactional
public class SellerService {

    @Autowired
    private SellerRepository SellerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Seller saveSeller(Seller Seller) {
        Seller.setPassword(passwordEncoder.encode(Seller.getPassword()));
        return SellerRepository.save(Seller);
    }

    public Optional<Seller> updateSeller(Long id, Seller SellerDetails) {
        
        return SellerRepository.findById(id)
            .map(existingSeller -> {
                
                existingSeller.setFirstName(SellerDetails.getFirstName());
                existingSeller.setMiddleName(SellerDetails.getMiddleName());
                existingSeller.setLastName(SellerDetails.getLastName());
                existingSeller.setEmail(SellerDetails.getEmail());
                existingSeller.setMobile(SellerDetails.getMobile());
                
                if (SellerDetails.getPassword() != null && !SellerDetails.getPassword().isEmpty()) {
                    existingSeller.setPassword(passwordEncoder.encode(SellerDetails.getPassword()));
                }

                existingSeller.setSellerType(SellerDetails.getSellerType());

                return SellerRepository.save(existingSeller);
            });
    }
}