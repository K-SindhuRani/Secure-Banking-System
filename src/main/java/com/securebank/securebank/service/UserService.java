package com.securebank.securebank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.securebank.securebank.entity.User;
import com.securebank.securebank.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Register User
    public User registerUser(User user) {

        // Encrypt password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    // Get All Users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get User By Id
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // Delete User
    public String deleteUser(Long id) {

        if (!userRepository.existsById(id)) {
            return "User not found";
        }

        userRepository.deleteById(id);
        return "User deleted successfully";
    }
}