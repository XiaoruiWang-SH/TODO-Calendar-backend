/*
 * @Author: Xiaorui Wang
 * @Email: xiaorui.wang@usi.ch
 * @Date: 2025-03-26 15:01:35
 * @LastEditors: Xiaorui Wang
 * @LastEditTime: 2025-03-27 17:56:38
 * @Description: 
 * Copyright (c) 2025 by Xiaorui Wang, All Rights Reserved. 
 */
package com.example.controller;

import com.example.model.Task;
import com.example.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<?> getAllTasks(@RequestBody Map<String, Object> params) {
        String date = (String) params.get("date");
        String startDate = (String) params.get("startDate");
        String endDate = (String) params.get("endDate");
        if (params.get("userId") == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("userId is required");
        }
        int userId = (int) params.get("userId");
        if (userId < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("userId is invalid");
        }

        if (date != null) {
            List<Task> tasks = taskService.findByDate(date, userId);
            if (tasks.isEmpty()) {
                return ResponseEntity.ok(new ArrayList<>());
            }
            return ResponseEntity.ok(tasks);
        }
        if (startDate != null && endDate != null) {
            List<Task> tasks = taskService.findByDateRange(startDate, endDate, userId);
            if (tasks.isEmpty()) {
                return ResponseEntity.ok(new ArrayList<>());
            }
            return ResponseEntity.ok(tasks);
        }
        return ResponseEntity.ok(taskService.findAll(userId));
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable("id") int id, @RequestBody Map<String, Object> params) {
        if (params.get("userId") == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("userId is required");
        }
        int userId = (int) params.get("userId");
        if (userId < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("userId is invalid");
        }
        return ResponseEntity.ok(taskService.findById(id, userId));
    }   

    @PostMapping("/create")
    public ResponseEntity<?> createTask(@RequestBody Map<String, Object> params) {
        if (params.get("userId") == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("userId is required");
        }
        int userId = (int) params.get("userId");
        if (userId < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("userId is invalid");
        }
        
        Map<String, Object> taskMap = (Map<String, Object>) params.get("task");
        if (taskMap == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("task is required");
        }

        Task task = new Task();
        task.setName((String) taskMap.get("name"));
        task.setChecked((Boolean) taskMap.get("checked"));
        task.setImportant((Boolean) taskMap.get("important"));
        task.setCreateTime((String) taskMap.get("createTime"));
        task.setExpireTime((String) taskMap.get("expireTime"));
        task.setUpdateTime((String) taskMap.get("updateTime"));
        task.setCreateDate((String) taskMap.get("createDate"));
        task.setUserId(userId);

        int result = taskService.insert(task, userId);
        if (result > 0) {
            return ResponseEntity.ok(task.getId());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(-1);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateTask(@PathVariable("id") int id, @RequestBody Map<String, Object> params) {
        if (params.get("userId") == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("userId is required");
        }
        int userId = (int) params.get("userId");
        if (userId < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("userId is invalid");
        }
        Map<String, Object> taskMap = (Map<String, Object>) params.get("task");
        if (taskMap == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("task is required");
        }

        Task task = new Task();
        task.setName((String) taskMap.get("name"));
        task.setChecked((Boolean) taskMap.get("checked"));
        task.setImportant((Boolean) taskMap.get("important"));
        task.setCreateTime((String) taskMap.get("createTime"));
        task.setExpireTime((String) taskMap.get("expireTime"));
        task.setUpdateTime((String) taskMap.get("updateTime"));
        task.setCreateDate((String) taskMap.get("createDate"));
        task.setUserId(userId);

        int result = taskService.update(task, id, userId);
        return result > 0 ? ResponseEntity.ok(true) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable("id") int id, @RequestBody Map<String, Object> params) {
        if (params.get("userId") == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("userId is required");
        }
        int userId = (int) params.get("userId");
        if (userId < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("userId is invalid");
        }
        int result = taskService.delete(id, userId);
        return result > 0 ? ResponseEntity.ok(true) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
    }
    
}
