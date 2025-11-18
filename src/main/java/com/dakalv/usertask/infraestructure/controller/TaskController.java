package com.dakalv.usertask.infraestructure.controller;

import com.dakalv.usertask.application.service.TaskService;
import com.dakalv.usertask.application.dto.task.CreateTaskDTO;
import com.dakalv.usertask.application.dto.task.TaskDTO;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService service){
        this.taskService = service;
    }

    @PostMapping("/user/{userId}")
    public TaskDTO createTask(@RequestBody CreateTaskDTO createTaskDTO, @PathVariable Long userId){
        return taskService.createTask(createTaskDTO, userId);
    }

    @GetMapping("/user/{userId}")
    public List<TaskDTO> getTasksByUser(@PathVariable Long userId){
        return taskService.getTasksByUser(userId);
    }
}
