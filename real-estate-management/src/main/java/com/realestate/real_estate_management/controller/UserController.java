package com.realestate.real_estate_management.controller;

import com.realestate.real_estate_management.entity.User;
import com.realestate.real_estate_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity; 
import org.springframework.http.HttpStatus; 
import org.springframework.web.bind.annotation.GetMapping; 
import java.security.Principal; 


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @GetMapping("/me")
    public ResponseEntity<User> getAuthenticatedUser(Principal principal) {
        
        // The Security filter ensures principal is not null, but we check anyway.
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); 
        }

        String email = principal.getName();
        User user = userService.findUserByEmail(email);

        if (user == null) {
            // This should not happen if the user is authenticated, but good practice
            return ResponseEntity.notFound().build();
        }
        
        // Returns the full User object (including the role via inheritance)
        return ResponseEntity.ok(user);
    }
}