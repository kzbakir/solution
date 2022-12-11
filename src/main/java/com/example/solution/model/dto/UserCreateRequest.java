package com.example.solution.model.dto;

import lombok.Data;

@Data
public class UserCreateRequest {
    private String username;
    private String password;
}
