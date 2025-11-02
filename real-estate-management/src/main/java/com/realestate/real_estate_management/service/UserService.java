package com.realestate.real_estate_management.service;

import com.realestate.real_estate_management.entity.Admin;
import com.realestate.real_estate_management.entity.Buyer;
import com.realestate.real_estate_management.entity.Seller;
import com.realestate.real_estate_management.entity.Tenant;
import com.realestate.real_estate_management.entity.User;
import com.realestate.real_estate_management.repository.AdminRepository;
import com.realestate.real_estate_management.repository.BoughtRepository;
import com.realestate.real_estate_management.repository.BuyerRepository;
import com.realestate.real_estate_management.repository.LeaseRepository;
import com.realestate.real_estate_management.repository.ReviewRepository;
import com.realestate.real_estate_management.repository.SellerRepository;
import com.realestate.real_estate_management.repository.TenantRepository;
import com.realestate.real_estate_management.repository.UserRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional  
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired private ReviewRepository reviewRepository;
    @Autowired private BoughtRepository boughtRepository;
    @Autowired private LeaseRepository leaseRepository;
    @Autowired private PropertyService propertyService;
    @Autowired private AdminRepository adminRepository;
    @Autowired private BuyerRepository buyerRepository;
    @Autowired private SellerRepository sellerRepository;
    @Autowired private TenantRepository tenantRepository;

    public void deleteUserByEmail(String email) {
        User user = findUserByEmail(email); // This already throws ResourceNotFoundException
        
        // 1. Delete all direct dependencies on the base 'User' table
        reviewRepository.deleteAllByUser(user);
        
        // 2. Delete all dependencies based on the user's role
        if (user instanceof Seller seller) {
            // A Seller is the most complex
            // Delete all their properties (and all property dependencies)
            propertyService.deleteAllPropertiesBySeller(seller);
            // Delete any Bought/Lease records where they were the seller
            boughtRepository.deleteAllBySeller(seller);
            leaseRepository.deleteAllBySeller(seller);
            // Finally, delete the seller record
            sellerRepository.delete(seller);
            
        } else if (user instanceof Buyer buyer) {
            // Delete all purchase records
            boughtRepository.deleteAllByBuyer(buyer);
            buyerRepository.delete(buyer);
            
        } else if (user instanceof Tenant tenant) {
            // Delete all lease records
            leaseRepository.deleteAllByTenant(tenant);
            tenantRepository.delete(tenant);
            
        } else if (user instanceof Admin admin) {
            // Admins are simple, just delete them
            adminRepository.delete(admin);
        }
        
        // 3. After all child-table records are gone, delete the base User
        userRepository.delete(user);
    }

    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}