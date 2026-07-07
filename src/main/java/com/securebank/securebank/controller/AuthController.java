package com.securebank.securebank.controller;

import com.securebank.securebank.dto.LoginRequest;
import com.securebank.securebank.dto.LoginResponse;
import com.securebank.securebank.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        return authenticationService.login(request);

    }

}