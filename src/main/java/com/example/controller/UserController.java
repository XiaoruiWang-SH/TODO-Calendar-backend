/*
 * @Author: Xiaorui Wang
 * @Email: xiaorui.wang@usi.ch
 * @Date: 2025-03-25 16:49:17
 * @LastEditors: Xiaorui Wang
 * @LastEditTime: 2025-03-25 16:51:48
 * @Description: 
 * @FilePath: /src/main/java/com/example/controller/UserController.java
 * Copyright (c) 2025 by Xiaorui Wang, All Rights Reserved. 
 */

package com.example.controller;

import com.example.model.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public int createUser(@RequestBody User user) {
        return userService.createUser(user);
    }
    
    @PutMapping("/{id}")
    public int updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }
}

