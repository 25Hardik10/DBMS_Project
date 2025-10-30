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
                
                // ... (all public and role-specific endpoints remain)
                .requestMatchers("/api/sellers/my-properties").hasAuthority(UserRoles.ROLE_SELLER)
                .requestMatchers("/api/buyers/my-purchases").hasAuthority(UserRoles.ROLE_BUYER)
                .requestMatchers("/api/tenants/my-leases").hasAuthority(UserRoles.ROLE_TENANT)

                .requestMatchers("/api/properties/{propertyId}/reviews").authenticated()
                .requestMatchers("/api/properties/{propertyId}/images").authenticated() 

                // --- ADMIN ENDPOINTS ---
                .requestMatchers("/api/properties/{id}").hasAuthority(UserRoles.ROLE_ADMIN)
                .requestMatchers("/api/users/{id}").hasAuthority(UserRoles.ROLE_ADMIN)
                .requestMatchers("/api/users/all").hasAuthority(UserRoles.ROLE_ADMIN) // <-- SECURE NEW ENDPOINT
                // --- END ADMIN ENDPOINTS ---

                .anyRequest().authenticated() 
            )
            .userDetailsService(customUserDetailsService) 
            .httpBasic(withDefaults());

        return http.build();
    }
}