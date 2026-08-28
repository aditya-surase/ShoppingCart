package com.shopping.shoppingcart.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shopping.shoppingcart.entity.User;
import com.shopping.shoppingcart.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Register User
    public User registerUser(User user) {

    	User existingUser =
    	        userRepository.findByEmail(user.getEmail()).orElse(null);
        if (existingUser != null) {
            return null;
        }

        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("CUSTOMER");
        }

        return userRepository.save(user);
    }

    // Login User
    public User loginUser(String email, String password) {

    	User user = userRepository.findByEmail(email).orElse(null);

        if (user != null && user.getPassword().equals(password)) {
            return user;
        }

        return null;
    }

    // Get All Users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get User By ID
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}