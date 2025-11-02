package com.realestate.real_estate_management.service;

import com.realestate.real_estate_management.entity.Admin;
import com.realestate.real_estate_management.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@Transactional
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Admin saveAdmin(Admin admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        return adminRepository.save(admin);
    }

    public Optional<Admin> updateAdmin(Long id, Admin adminDetails) {
        
        return adminRepository.findById(id)
            .map(existingAdmin -> {
                
                existingAdmin.setFirstName(adminDetails.getFirstName());
                existingAdmin.setMiddleName(adminDetails.getMiddleName());
                existingAdmin.setLastName(adminDetails.getLastName());
                existingAdmin.setEmail(adminDetails.getEmail());
                existingAdmin.setMobile(adminDetails.getMobile());
                
                if (adminDetails.getPassword() != null && !adminDetails.getPassword().isEmpty()) {
                    existingAdmin.setPassword(passwordEncoder.encode(adminDetails.getPassword()));
                }

                existingAdmin.setAdminPrivileges(adminDetails.getAdminPrivileges());

                return adminRepository.save(existingAdmin);
            });
    }
}