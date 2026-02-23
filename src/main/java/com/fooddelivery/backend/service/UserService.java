package com.fooddelivery.backend.service;

import com.fooddelivery.backend.dto.CreateUserRequest;
import com.fooddelivery.backend.dto.UpdateProfileRequest;
import com.fooddelivery.backend.dto.UserResponse;
import com.fooddelivery.backend.exception.ResourceNotFoundException;
import com.fooddelivery.backend.model.User;
import com.fooddelivery.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // CREATE USER
    public UserResponse createUser(CreateUserRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        User savedUser = userRepository.save(user);

        return mapToResponse(savedUser);
    }

    // GET ALL USERS
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // GET USER BY ID
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
        return mapToResponse(user);
    }

    public UserResponse getProfileByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return mapToResponse(user);
    }

    public UserResponse updateProfile(String email, UpdateProfileRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setName(request.getName());
        return mapToResponse(userRepository.save(user));
    }

    // 🔥 MAPPING METHOD (VERY IMPORTANT)
    private UserResponse mapToResponse(User user) {
    return UserResponse.builder()
            .id(user.getId())
            .name(user.getName())
            .email(user.getEmail())
            .role(user.getRole().name())
            .createdAt(user.getCreatedAt())
            .build();
        }
}
