package com.fooddelivery.backend.controller;

import com.fooddelivery.backend.model.User;
import com.fooddelivery.backend.repository.UserRepository;
import com.fooddelivery.backend.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.fooddelivery.backend.dto.AuthResponse;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
public Map<String, String> register(@RequestBody User user) {

    if (userRepository.findByEmail(user.getEmail()).isPresent()) {
        throw new RuntimeException("Email already registered");
    }

    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.save(user);

    return Map.of("message", "User registered successfully");
}

    @PostMapping("/login")
    public AuthResponse login(@RequestBody User user) {
    
        User existingUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
    
        if (!passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
    
        String token = jwtUtil.generateToken(user.getEmail());
    
        return new AuthResponse(token);
    }
}