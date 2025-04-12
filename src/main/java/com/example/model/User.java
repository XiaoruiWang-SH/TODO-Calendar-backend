/*
 * @Author: Xiaorui Wang
 * @Email: xiaorui.wang@usi.ch
 * @Date: 2025-03-25 16:25:23
 * @LastEditors: Xiaorui Wang
 * @LastEditTime: 2025-04-08 08:30:32
 * @Description: 
 * Copyright (c) 2025 by Xiaorui Wang, All Rights Reserved. 
 */
package com.example.model;

import lombok.Data;

@Data
public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private String role;
    private String provider;
    private String providerId;
}
