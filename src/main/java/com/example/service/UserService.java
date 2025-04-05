/*
 * @Author: Xiaorui Wang
 * @Email: xiaorui.wang@usi.ch
 * @Date: 2025-03-25 16:40:52
 * @LastEditors: Xiaorui Wang
 * @LastEditTime: 2025-04-05 14:41:48
 * @Description: 
 * Copyright (c) 2025 by Xiaorui Wang, All Rights Reserved. 
 */

package com.example.service;
import com.example.model.User;
import com.example.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class UserService {
    private final UserMapper userMapper;
    
    
    @Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public List<User> getAllUsers() {
        return userMapper.findAll();
    }
    
    public User getUserByEmailAndPassword(String email, String password) {
        return userMapper.findByEmailAndPassword(email, password);
    }

    public int createUser(User user) {
        return userMapper.insert(user);
    }

    public int updateUser(User user) {
        return userMapper.update(user);
    }

    public int deleteUser(int id) {
        return userMapper.delete(id);
    }

    public boolean checkUserExists(String email) {
        return userMapper.checkUserEmailExists(email) > 0;
    }

    public User getUserByEmail(String email) {
        if (!checkUserExists(email)) {
            return null;
        }
        return userMapper.findByEmail(email);
    }
}
