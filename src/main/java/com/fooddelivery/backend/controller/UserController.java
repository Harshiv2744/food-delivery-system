package com.fooddelivery.backend.controller;

import com.fooddelivery.backend.dto.CreateUserRequest;
import com.fooddelivery.backend.model.User;
import com.fooddelivery.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
public User createUser(@Valid @RequestBody CreateUserRequest request) {

    User user = User.builder()
            .name(request.getName())
            .email(request.getEmail())
            .password(request.getPassword())
            .role(request.getRole())
            .createdAt(LocalDateTime.now())
            .build();

    return userService.createUser(user);
}


    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }
}
