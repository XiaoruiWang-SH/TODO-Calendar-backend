/*
 * @Author: Xiaorui Wang
 * @Email: xiaorui.wang@usi.ch
 * @Date: 2025-03-26 15:02:06
 * @LastEditors: Xiaorui Wang
 * @LastEditTime: 2025-03-27 16:49:33
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

@Service
@Transactional
public class TaskService {
    private final TaskMapper taskMapper;

    @Autowired
    public TaskService(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    public List<Task> findAll(int userId) {
        return taskMapper.findAll(userId);
    }

    public Task findById(int id, int userId) {
        return taskMapper.findById(id, userId);
    }

    public List<Task> findByDate(String date, int userId) {
        return taskMapper.findByDate(date, userId);
    }

    public List<Task> findByDateRange(String startDate, String endDate, int userId) {
        return taskMapper.findByDateRange(startDate, endDate, userId);
    }

    public int update(Task task, int id, int userId) {
        return taskMapper.update(task, id, userId);
    }

    public int insert(Task task, int userId) {
        return taskMapper.insert(task, userId);
    }

    public int delete(int id, int userId) {
        return taskMapper.delete(id, userId);
    }
    
    
}
