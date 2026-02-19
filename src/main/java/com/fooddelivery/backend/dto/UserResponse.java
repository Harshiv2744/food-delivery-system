package com.fooddelivery.backend.dto;

import com.fooddelivery.backend.enums.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {

    private Long id;
    private String name;
    private String email;
    private Role role;
}
