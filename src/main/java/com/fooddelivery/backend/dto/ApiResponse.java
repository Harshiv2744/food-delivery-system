package com.fooddelivery.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.fooddelivery.backend.dto.ApiResponse;

@Data
@AllArgsConstructor
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;
}