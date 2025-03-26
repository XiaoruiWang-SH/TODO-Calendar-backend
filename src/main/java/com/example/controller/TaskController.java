/*
 * @Author: Xiaorui Wang
 * @Email: xiaorui.wang@usi.ch
 * @Date: 2025-03-26 15:01:35
 * @LastEditors: Xiaorui Wang
 * @LastEditTime: 2025-03-26 21:58:50
 * @Description: 
 * Copyright (c) 2025 by Xiaorui Wang, All Rights Reserved. 
 */
package com.example.controller;

import com.example.model.Task;
import com.example.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> getAllTasks(
                                @RequestParam(value = "date", required = false) String date,
                                @RequestParam(value = "startDate", required = false) String startDate,
                                @RequestParam(value = "endDate", required = false) String endDate) {
        if (date != null) {
            return taskService.findByDate(date);
        }
        if (startDate != null && endDate != null) {
            return taskService.findByDateRange(startDate, endDate);
        }
        return taskService.findAll();
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable("id") int id) {
        return taskService.findById(id);
    }   

    @PostMapping
    public int createTask(@RequestBody Task task) {
        int result = taskService.insert(task);
        if (result > 0) {
            return task.getId();
        }
        return -1;
    }

    @PostMapping("/{id}")
    public boolean updateTask(@PathVariable("id") int id, @RequestBody Task task) {
        int result = taskService.update(task, id);
        return result > 0;
    }

    @DeleteMapping("/{id}")
    public boolean deleteTask(@PathVariable("id") int id) {
        int result = taskService.delete(id);
        return result > 0;
    }
    
}
