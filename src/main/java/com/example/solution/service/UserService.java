package com.example.solution.service;

import com.example.solution.model.dto.LoginRequest;
import com.example.solution.model.dto.UserCreateRequest;

public interface UserService {

    void createUser(UserCreateRequest request);

    void login(LoginRequest request);
}
