/*
 * @Author: Xiaorui Wang
 * @Email: xiaorui.wang@usi.ch
 * @Date: 2025-03-26 15:02:25
 * @LastEditors: Xiaorui Wang
 * @LastEditTime: 2025-04-05 14:26:15
 * @Description: 
 * Copyright (c) 2025 by Xiaorui Wang, All Rights Reserved. 
 */
package com.example.model;

import lombok.Data;

@Data
public class Task {
    private int id;
    private String name;
    private boolean checked;
    private boolean important;
    private String createTime;
    private String expireTime;
    private String updateTime;
    private String createDate;
    private int userId;

}

