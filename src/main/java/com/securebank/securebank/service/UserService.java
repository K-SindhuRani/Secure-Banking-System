package com.securebank.securebank.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.securebank.securebank.entity.User;
import com.securebank.securebank.repository.UserRepository;

@Service
public class UserService {

    private static final Logger logger =
            LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Register User
    public User registerUser(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(user);

        logger.info("New User Registered : {}", savedUser.getEmail());

        return savedUser;
    }

    // Get All Users
    public List<User> getAllUsers() {

        logger.info("Fetching All Users");

        return userRepository.findAll();
    }

    // Get User By Id
    public User getUserById(Long id) {

        logger.info("Fetching User : {}", id);

        return userRepository.findById(id).orElse(null);
    }

    // Delete User
    public String deleteUser(Long id) {

        if (!userRepository.existsById(id)) {

            logger.error("Delete Failed : User {} Not Found", id);

            return "User not found";
        }

        userRepository.deleteById(id);

        logger.info("User Deleted : {}", id);

        return "User deleted successfully";
    }
}