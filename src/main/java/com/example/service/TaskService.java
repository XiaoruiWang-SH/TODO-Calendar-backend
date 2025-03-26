/*
 * @Author: Xiaorui Wang
 * @Email: xiaorui.wang@usi.ch
 * @Date: 2025-03-26 15:02:06
 * @LastEditors: Xiaorui Wang
 * @LastEditTime: 2025-03-26 21:56:48
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

    public List<Task> findAll() {
        return taskMapper.findAll();
    }

    public Task findById(int id) {
        return taskMapper.findById(id);
    }

    public List<Task> findByDate(String date) {
        return taskMapper.findByDate(date);
    }

    public List<Task> findByDateRange(String startDate, String endDate) {
        return taskMapper.findByDateRange(startDate, endDate);
    }

    public int update(Task task, int id) {
        return taskMapper.update(task, id);
    }

    public int insert(Task task) {
        return taskMapper.insert(task);
    }

    public int delete(int id) {
        return taskMapper.delete(id);
    }
    
    
}
