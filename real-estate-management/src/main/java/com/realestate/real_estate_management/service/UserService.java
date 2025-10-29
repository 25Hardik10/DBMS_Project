package com.realestate.real_estate_management.service;

import com.realestate.real_estate_management.entity.User;
import com.realestate.real_estate_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }
}