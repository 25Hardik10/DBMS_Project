package com.realestate.real_estate_management.config;

import com.realestate.real_estate_management.security.UserRoles;
import com.realestate.real_estate_management.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

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

                        // --- (Public rules are correct) ---
                        .requestMatchers("/", "/login", "/register", "/details", "/upload", "/profile", "/admin",
                                "/edit-profile", "/login?error", "/login?logout")
                        .permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/properties/all", "/api/properties/search").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/properties/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/properties/{propertyId}/images").permitAll()
                        .requestMatchers("/api/users", "/api/sellers", "/api/buyers", "/api/tenants").permitAll()

                        // --- (Role-specific rules are correct) ---
                        .requestMatchers("/api/flats", "/api/lands", "/api.commercials", "/api/rentals",
                                "/api/colivings")
                        .hasAuthority(UserRoles.ROLE_SELLER)
                        .requestMatchers("/api/flats/**", "/api/lands/**", "/api/commercials/**", "/api/rentals/**",
                                "/api/colivings/**")
                        .hasAuthority(UserRoles.ROLE_SELLER)
                        .requestMatchers("/api/sellers/my-properties").hasAuthority(UserRoles.ROLE_SELLER)
                        .requestMatchers("/api/buyers/my-purchases").hasAuthority(UserRoles.ROLE_BUYER)
                        .requestMatchers("/api/tenants/my-leases").hasAuthority(UserRoles.ROLE_TENANT)
                        .requestMatchers("/api/properties/{propertyId}/reviews").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/properties/{propertyId}/images").authenticated()

                        .requestMatchers(HttpMethod.DELETE, "/api/properties/my-properties/{id}")
                        .hasAuthority(UserRoles.ROLE_SELLER)
                        .requestMatchers("/", "/login", "/register", "/details", "/upload", "/profile", "/admin",
                                "/edit-profile", "/edit-property", "/login?error", "/login?logout")
                        .permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/users/me").authenticated()
                        // --- FIX: ADD THIS NEW RULE HERE ---
                        // This rule must come BEFORE the /api/users/{id} rule.
                        .requestMatchers("/api/users/me").authenticated()
                        // --- END FIX ---

                        // --- (Admin rules are correct) ---
                        .requestMatchers(HttpMethod.PUT, "/api/properties/{id}").hasAuthority(UserRoles.ROLE_ADMIN)
                        .requestMatchers(HttpMethod.DELETE, "/api/properties/{id}").hasAuthority(UserRoles.ROLE_ADMIN)
                        .requestMatchers("/api/users/{id}").hasAuthority(UserRoles.ROLE_ADMIN)
                        .requestMatchers("/api/users/all").hasAuthority(UserRoles.ROLE_ADMIN)
                        .anyRequest().authenticated())
                .userDetailsService(customUserDetailsService)
                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("email")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/login?error=true")
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll());
        // (The .httpBasic() line is correctly removed)

        return http.build();
    }
}