/*
 * @Author: Xiaorui Wang
 * @Email: xiaorui.wang@usi.ch
 * @Date: 2025-04-05 13:57:26
 * @LastEditors: Xiaorui Wang
 * @LastEditTime: 2025-04-08 09:54:06
 * @Description: 
 * Copyright (c) 2025 by Xiaorui Wang, All Rights Reserved. 
 */
package com.example.Tools;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import jakarta.annotation.Nonnull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collections;
import java.util.Arrays;
import java.util.Optional;

public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request, 
                                   @Nonnull HttpServletResponse response, 
                                   @Nonnull FilterChain filterChain)
            throws ServletException, IOException {
        String token = getTokenFromRequest(request);
        
        try {
            if (token != null && jwtTokenProvider.validateToken(token)) {
                Authentication auth = getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
        }
        
        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            Optional<Cookie> tokenCookie = Arrays.stream(cookies)
                .filter(cookie -> "access_token".equals(cookie.getName()))
                .findFirst();
            
            if (tokenCookie.isPresent()) {
                return tokenCookie.get().getValue();
            }
        }
        
        return null;
    }
    
    private Authentication getAuthentication(String token) {
        String username = jwtTokenProvider.getUsernameFromToken(token);
        if (username != null) {
            UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                username, "", Collections.emptyList());
            return new UsernamePasswordAuthenticationToken(
                userDetails, "", userDetails.getAuthorities());
        }
        return null;
    }
} 