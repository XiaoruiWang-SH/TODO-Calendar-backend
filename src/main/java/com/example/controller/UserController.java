/*
 * @Author: Xiaorui Wang
 * @Email: xiaorui.wang@usi.ch
 * @Date: 2025-03-25 16:49:17
 * @LastEditors: Xiaorui Wang
 * @LastEditTime: 2025-04-08 08:32:27
 * @Description: 
 * @FilePath: /src/main/java/com/example/controller/UserController.java
 * Copyright (c) 2025 by Xiaorui Wang, All Rights Reserved. 
 */

package com.example.controller;

import com.example.model.User;
import com.example.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.ok(new ArrayList<>());
        }
        return ResponseEntity.ok(users);
    }

    @PostMapping("/user")
    public ResponseEntity<?> getUserByEmailAndPassword(@RequestBody User user) {
        System.out.println(user.getEmail());
        System.out.println(user.getPassword());
        User result = userService.getUserByEmailAndPassword(user.getEmail(), user.getPassword());
        if (result == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");
        }
        return ResponseEntity.ok(result);
    }

    
    @PostMapping("/{id}")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        int result = userService.updateUser(user);
        if (result > 0) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(-1);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        int result = userService.deleteUser(id);
        if (result > 0) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(-1);
    }

}

