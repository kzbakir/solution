package com.example.solution.service.impl;

import com.example.solution.model.dto.LoginRequest;
import com.example.solution.model.dto.UserCreateRequest;
import com.example.solution.model.entity.User;
import com.example.solution.model.enums.Role;
import com.example.solution.repository.UserRepository;
import com.example.solution.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public void createUser(UserCreateRequest request) {
        var user = userRepository.findByUsername(request.getUsername());
        if (user.isPresent()) {
            throw new RuntimeException("User exist: " + request.getUsername());
        }
        userRepository.save(User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(List.of(Role.USER))
                .build());
    }

    @Override
    public void login(LoginRequest request) {
        var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
