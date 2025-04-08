/*
 * @Author: Xiaorui Wang
 * @Email: xiaorui.wang@usi.ch
 * @Date: 2025-03-26 15:02:06
 * @LastEditors: Xiaorui Wang
 * @LastEditTime: 2025-04-08 08:14:26
 * @Description: 
 * Copyright (c) 2025 by Xiaorui Wang, All Rights Reserved. 
 */
package com.example.service;
import com.example.model.Task;
import com.example.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.RequiredArgsConstructor;
import com.example.model.User;
import com.example.Tools.UserUtil;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskService {
    @Autowired
    private final TaskMapper taskMapper;

   
    public Task findById(int id) {
        return taskMapper.findById(id);
    }

    public List<Task> findByDate(String date) {
        String userEmail = UserUtil.getCurrentUsername();
        return taskMapper.findByDate(date, userEmail);
    }

    public List<Task> findByDateRange(String startDate, String endDate) {   
        String userEmail = UserUtil.getCurrentUsername();
        return taskMapper.findByDateRange(startDate, endDate, userEmail);
    }

    public int update(Task task, int id) {
        return taskMapper.update(task, id);
    }

    public int insert(Task task) {
        String userEmail = UserUtil.getCurrentUsername();
        return taskMapper.insert(task, userEmail);
    }

    public int delete(int id) {
        String userEmail = UserUtil.getCurrentUsername();
        return taskMapper.delete(id, userEmail);
    }
    
    
}
