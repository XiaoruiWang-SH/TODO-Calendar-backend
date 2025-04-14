/*
 * @Author: Xiaorui Wang
 * @Email: xiaorui.wang@usi.ch
 * @Date: 2025-03-26 15:01:35
 * @LastEditors: Xiaorui Wang
 * @LastEditTime: 2025-04-08 10:03:46
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
        
        if (date != null) {
            List<Task> tasks = taskService.findByDate(date);
            if (tasks.isEmpty()) {
                return ResponseEntity.ok(new ArrayList<>());
            }
            return ResponseEntity.ok(tasks);
        }
        if (startDate != null && endDate != null) {
            List<Task> tasks = taskService.findByDateRange(startDate, endDate);
            if (tasks.isEmpty()) {
                return ResponseEntity.ok(new ArrayList<>());
            }
            return ResponseEntity.ok(tasks);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request");
    }


    @PostMapping("/create")
    public ResponseEntity<?> createTask(@RequestBody Map<String, Object> params) {
        if (params == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("task is required");
        }
        Task task = new Task();
        task.setTitle((String) params.get("title"));
        task.setDetails((String) params.get("details"));
        task.setChecked((Boolean) params.get("checked"));
        task.setImportant((Boolean) params.get("important"));
        task.setCreateTime((String) params.get("createTime"));
        task.setExpireTime((String) params.get("expireTime"));
        task.setUpdateTime((String) params.get("updateTime"));
        task.setCreateDate((String) params.get("createDate"));

        int result = taskService.insert(task);
        if (result > 0) {
            return ResponseEntity.ok(task.getId());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create task");
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateTask(@PathVariable("id") int id, @RequestBody Map<String, Object> params) {
        if (params == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("task is required");
        }

        Task task = new Task();
        task.setTitle((String) params.get("title"));
        task.setDetails((String) params.get("details"));
        task.setChecked((Boolean) params.get("checked"));
        task.setImportant((Boolean) params.get("important"));
        task.setCreateTime((String) params.get("createTime"));
        task.setExpireTime((String) params.get("expireTime"));
        task.setUpdateTime((String) params.get("updateTime"));
        task.setCreateDate((String) params.get("createDate"));

        int result = taskService.update(task, id);
        return result > 0 ? ResponseEntity.ok("update Success") : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update task");
    }    

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable("id") int id) {
        int result = taskService.delete(id);
        return result > 0 ? ResponseEntity.ok("delete Success") : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to delete task");
    }
}
