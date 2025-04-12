/*
 * @Author: Xiaorui Wang
 * @Email: xiaorui.wang@usi.ch
 * @Date: 2025-03-26 09:25:17
 * @LastEditors: Xiaorui Wang
 * @LastEditTime: 2025-04-08 08:29:50
 * @Description: 
 * Copyright (c) 2025 by Xiaorui Wang, All Rights Reserved. 
 */
package com.example.mapper;

import com.example.model.User;
import org.apache.ibatis.annotations.*;
import java.util.List;



@Mapper
public interface UserMapper {
    @Select("SELECT * FROM users")
    List<User> findAll();

    @Select("SELECT * FROM users WHERE id = #{id}")
    User findById(@Param("id") int id);

    @Select("SELECT * FROM users WHERE email = #{email}")
    User findByEmail(@Param("email") String email);

    @Select("SELECT * FROM users WHERE email = #{email} AND password = #{password}")
    User findByEmailAndPassword(@Param("email") String email, @Param("password") String password);

    @Insert("INSERT INTO users (name, email, password, role, provider, providerId) VALUES (#{user.name}, #{user.email}, #{user.password}, #{user.role}, #{user.provider}, #{user.providerId})")
    @Options(useGeneratedKeys = true, keyProperty = "user.id")
    int insert(@Param("user") User user);

    @Update("UPDATE users SET name = #{user.name}, email = #{user.email}, password = #{user.password}, role = #{user.role}, provider = #{user.provider}, providerId = #{user.providerId} WHERE id = #{user.id}")
    int update(@Param("user") User user);

    @Delete("DELETE FROM users WHERE id = #{id}")
    int delete(@Param("id") int id);

    @Select("SELECT COUNT(*) FROM users WHERE email = #{email}")
    int checkUserEmailExists(@Param("email") String email);


} 

/**
 * CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL
);
 */