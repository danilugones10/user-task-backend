package com.dakalv.usertask.web;

import com.dakalv.usertask.application.TaskService;
import com.dakalv.usertask.domain.Task;
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
    public Task createTask(@RequestBody Task task, @PathVariable Long userId){
        return taskService.createTask(task, userId);
    }

    @GetMapping("/user/{userId}")
    public List<Task> getTasksByUser(@PathVariable Long userId){
        return taskService.getTasksByUser(userId);
    }

    @PutMapping("/{id}")
    public void updateTask(@PathVariable Long id, @RequestBody Task task){
        task.setId(id);
        taskService.updateTask(task);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id){
        taskService.deleteTask(id);
    }
}
