package com.realestate.real_estate_management.service;

import com.realestate.real_estate_management.entity.Admin;
import com.realestate.real_estate_management.entity.Buyer;
import com.realestate.real_estate_management.entity.Seller;
import com.realestate.real_estate_management.entity.Tenant;
import com.realestate.real_estate_management.entity.User;
import com.realestate.real_estate_management.repository.UserRepository;
import com.realestate.real_estate_management.security.UserRoles; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        User user = userRepository.findByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }

        String role = determineRole(user);

        Collection<? extends GrantedAuthority> authorities = 
            Collections.singletonList(new SimpleGrantedAuthority(role));

        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),          
            user.getPassword(),       
            authorities   
        );
    }

    private String determineRole(User user) {
        if (user instanceof Admin) {
            return UserRoles.ROLE_ADMIN;
        } else if (user instanceof Seller) {
            return UserRoles.ROLE_SELLER;
        } else if (user instanceof Buyer) {
            return UserRoles.ROLE_BUYER;
        } else if (user instanceof Tenant) {
            return UserRoles.ROLE_TENANT;
        } else {
            return UserRoles.ROLE_USER;
        }
    }
}