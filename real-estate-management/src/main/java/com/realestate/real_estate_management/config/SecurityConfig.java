package com.realestate.real_estate_management.config;

import com.realestate.real_estate_management.security.UserRoles; // <-- Import roles class
import com.realestate.real_estate_management.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                
                // --- PUBLIC ENDPOINTS ---
                // Public Read Access for Properties and Search
                .requestMatchers("/api/properties/all", "/api/properties/search").permitAll()
                .requestMatchers("/api/properties/{id}").permitAll()
                .requestMatchers("/api/properties/{propertyId}/images").permitAll() // View images is public

                // Public Registration Endpoints
                .requestMatchers("/api/users", "/api/sellers", "/api/buyers", "/api/tenants").permitAll()
                
                // --- AUTHORIZED ENDPOINTS ---
                // Only Sellers can POST a new property (Flat, Land, Commercial, etc.)
                .requestMatchers("/api/flats", "/api/lands", "/api/commercials", "/api/rentals", "/api/colivings").hasAuthority(UserRoles.ROLE_SELLER)
                
                // Only Sellers can update their properties
                .requestMatchers("/api/flats/**", "/api/lands/**", "/api/commercials/**", "/api/rentals/**", "/api/colivings/**").hasAuthority(UserRoles.ROLE_SELLER)

                // Only any authenticated user can post a review
                .requestMatchers("/api/properties/{propertyId}/reviews").authenticated()
                .requestMatchers("/api/properties/{propertyId}/images").authenticated() // Posting an image requires login

                // Only Admin can DELETE properties/users
                .requestMatchers("/api/properties/{id}", "/api/users/{id}").hasAuthority(UserRoles.ROLE_ADMIN)

                // Require login for everything else (e.g., PUT methods for Buyer/Tenant, general user endpoints)
                .anyRequest().authenticated() 
            )
            .userDetailsService(customUserDetailsService) 
            .httpBasic(withDefaults());

        return http.build();
    }
}