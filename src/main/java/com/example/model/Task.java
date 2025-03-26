/*
 * @Author: Xiaorui Wang
 * @Email: xiaorui.wang@usi.ch
 * @Date: 2025-03-26 15:02:25
 * @LastEditors: Xiaorui Wang
 * @LastEditTime: 2025-03-26 15:07:54
 * @Description: 
 * Copyright (c) 2025 by Xiaorui Wang, All Rights Reserved. 
 */
package com.example.model;

public class Task {
    private int id;
    private String name;
    private boolean checked;
    private boolean important;
    private String createTime;
    private String expireTime;
    private String updateTime;
    private String createDate;


    public Task() {}

    public Task(int id, 
                String name, 
                boolean checked, 
                boolean important, 
                String createTime, 
                String expireTime, 
                String updateTime, 
                String createDate) {
        this.id = id;
        this.name = name;
        this.checked = checked;
        this.important = important;
        this.createTime = createTime;
        this.expireTime = expireTime;
        this.updateTime = updateTime;
        this.createDate = createDate;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    public boolean isChecked() {
        return checked;
    }

    public boolean isImportant() {
        return important;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public String getCreateDate() {
        return createDate;
    }
    
    
    
}

