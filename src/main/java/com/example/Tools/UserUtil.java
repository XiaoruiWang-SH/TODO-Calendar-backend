/*
 * @Author: Xiaorui Wang
 * @Email: xiaorui.wang@usi.ch
 * @Date: 2025-04-08 08:10:26
 * @LastEditors: Xiaorui Wang
 * @LastEditTime: 2025-04-08 07:56:27
 * @Description: Utility class to get the currently authenticated user
 * Copyright (c) 2025 by Xiaorui Wang, All Rights Reserved. 
 */
package com.example.Tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.model.User;
import com.example.service.UserService;

/**
 * Utility class for accessing authenticated user information
 */
@Component
public class UserUtil {
    
    private static UserService userService;
    
    @Autowired
    public UserUtil(UserService userService) {
        UserUtil.userService = userService;
    }

    /**
     * Get the username (email) of the currently authenticated user
     * @return the username (email) or null if not authenticated
     */
    public static String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        
        Object principal = authentication.getPrincipal();
        
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else if (principal instanceof String) {
            return (String) principal;
        }
        
        return null;
    }
    
    /**
     * Get the full User object of the currently authenticated user
     * @return the User or null if not authenticated or user not found
     */
    // public static User getCurrentUser() {
    //     String username = getCurrentUsername();
    //     if (username == null || userService == null) {
    //         return null;
    //     }
        
    //     return userService.getUserByEmail(username);
    // }
} 