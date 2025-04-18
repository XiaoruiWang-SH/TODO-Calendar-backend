/*
 * @Author: Xiaorui Wang
 * @Email: xiaorui.wang@usi.ch
 * @Date: 2025-04-05 14:19:07
 * @LastEditors: Xiaorui Wang
 * @LastEditTime: 2025-04-14 07:47:40
 * @Description:    
 * Copyright (c) 2025 by Xiaorui Wang, All Rights Reserved. 
 */
package com.example.controller;

import com.example.model.User;
import com.example.service.UserService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.model.RegisterRequest;
import com.example.model.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.example.Tools.JwtTokenProvider;
import java.util.Map;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private final UserService userService;
    @Autowired
    private final UserDetailsService userDetailsService;
    @Autowired
    private final JwtTokenProvider jwtTokenProvider;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        if (userService.checkUserExists(registerRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User already exists");
        }
        User user = new User();
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole("USER");
        int result = userService.createUser(user);
        if (result <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to create user");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String token = null;
        try {
            token = jwtTokenProvider.generateToken(userDetails);
        } catch (Exception e) {
            System.out.println(e);
        }

        ResponseCookie tokenCookie = ResponseCookie.from("access_token", token)
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path("/")
                .maxAge(jwtTokenProvider.getExpirationTime() / 1000)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Set-Cookie", tokenCookie.toString());

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(Map.of(
                        "id", user.getId(),
                        "name", user.getName(),
                        "email", user.getEmail(),
                        "role", userDetails.getAuthorities().iterator().next().getAuthority()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        if (!userService.checkUserExists(loginRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User does not exist, please register first");
        }
        User user = userService.getUserByEmail(loginRequest.getEmail());
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Wrong password");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String token = null;
        try {
            token = jwtTokenProvider.generateToken(userDetails);
        } catch (Exception e) {
            System.out.println(e);
        }

        ResponseCookie tokenCookie = ResponseCookie.from("access_token", token)
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path("/")
                .maxAge(jwtTokenProvider.getExpirationTime() / 1000)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Set-Cookie", tokenCookie.toString());

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(Map.of(
                        "id", user.getId(),
                        "name", user.getName(),
                        "email", user.getEmail(),
                        "role", userDetails.getAuthorities().iterator().next().getAuthority()));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // Create an expired cookie to invalidate the token
        ResponseCookie tokenCookie = ResponseCookie.from("access_token", "")
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path("/")
                .maxAge(0) // Expire immediately
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Set-Cookie", tokenCookie.toString());

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(Map.of("message", "Logged out successfully"));
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserInfo(Authentication authentication) {
        try {
            if (authentication == null || !authentication.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Not authenticated"));
            }
            
            String email = authentication.getName();
            User user = userService.getUserByEmail(email);
            
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "User not found"));
            }
            return ResponseEntity.ok(Map.of(
                    "id", user.getId(),
                    "name", user.getName(),
                    "email", user.getEmail(),
                    "role", user.getRole()));
        } catch (Exception e) {
            System.out.println("Error in getUserInfo: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Error retrieving user information", 
                                 "error", e.getMessage()));
        }
    }

}
