/*
 * @Author: Xiaorui Wang
 * @Email: xiaorui.wang@usi.ch
 * @Date: 2025-03-25 16:40:52
 * @LastEditors: Xiaorui Wang
 * @LastEditTime: 2025-03-25 16:42:56
 * @Description: 
 * Copyright (c) 2025 by Xiaorui Wang, All Rights Reserved. 
 */

package com.example.service;
import com.example.model.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(int id) {
        return userRepository.findById(id);
    }

    public int createUser(User user) {
        return userRepository.save(user);
    }

    public int updateUser(User user) {
        return userRepository.update(user);
    }

    public int deleteUser(int id) {
        return userRepository.delete(id);
    }
}
