package com.example.solution.controller;

import com.example.solution.model.dto.LoginRequest;
import com.example.solution.model.dto.UserCreateRequest;
import com.example.solution.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> createUser(@RequestBody UserCreateRequest request) {
        userService.createUser(request);
        return new ResponseEntity<>("User was created!", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        userService.login(request);
        return new ResponseEntity<>("User signed-in successfully!", HttpStatus.OK);
    }
}
