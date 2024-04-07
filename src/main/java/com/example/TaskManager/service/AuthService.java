package com.example.TaskManager.service;

import com.example.TaskManager.Exception.UserNotFoundException;
import com.example.TaskManager.model.User;
import com.example.TaskManager.utills.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserService userService;
    private final JWTUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(UserService userService, JWTUtils jwtUtils, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public User signUp(User request) {
        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRole(request.getRole() == null ? "user" : request.getRole());

        try {
            User savedUser = userService.addUser(newUser);
            if (savedUser != null && savedUser.getId() > 0) {
                return savedUser;
            } else {
                throw new UserNotFoundException("Failed to save user");
            }
        } catch (Exception e) {
            throw new UserNotFoundException("Failed to save user", e);
        }
    }

    public String signIn(User user) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
            User authenticatedUser = userService.getUserByEmail(user.getEmail());
            if (authenticatedUser != null) {
                String jwt = jwtUtils.generateToken(authenticatedUser);
                return jwt;
            } else {
                throw new UserNotFoundException("User not found");
            }
        } catch (Exception e) {
            throw new UserNotFoundException("Authentication failed", e);
        }
    }
}
