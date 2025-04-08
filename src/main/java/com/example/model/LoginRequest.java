/*
 * @Author: Xiaorui Wang
 * @Email: xiaorui.wang@usi.ch
 * @Date: 2025-04-05 15:11:11
 * @LastEditors: Xiaorui Wang
 * @LastEditTime: 2025-04-05 15:11:28
 * @Description: 
 * Copyright (c) 2025 by Xiaorui Wang, All Rights Reserved. 
 */
package com.example.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
   
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}

