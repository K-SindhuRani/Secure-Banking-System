package com.securebank.securebank.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.securebank.securebank.dto.LoginRequest;
import com.securebank.securebank.dto.LoginResponse;
import com.securebank.securebank.entity.User;
import com.securebank.securebank.repository.UserRepository;
import com.securebank.securebank.security.JwtService;

@Service
public class AuthenticationService {

    private static final Logger logger =
            LoggerFactory.getLogger(AuthenticationService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                    logger.error("Login Failed : Email {} Not Found", request.getEmail());
                    return new RuntimeException("Invalid Email");
                });

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {

            logger.error("Login Failed : Invalid Password for {}", request.getEmail());

            throw new RuntimeException("Invalid Password");
        }

        String token = jwtService.generateToken(user.getEmail());

        logger.info("User Logged In Successfully : {}", user.getEmail());

        return new LoginResponse(token, "Login Successful");
    }
}