// TaskServiceImpl.java
package com.dakalv.usertask.application.impl;

import com.dakalv.usertask.application.TaskService;
import com.dakalv.usertask.domain.Task;
import com.dakalv.usertask.domain.User;
import com.dakalv.usertask.infraestructure.repository.TaskRepository;
import com.dakalv.usertask.infraestructure.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskServiceImpl(TaskRepository taskRepo, UserRepository userRepo){
        this.taskRepository = taskRepo;
        this.userRepository = userRepo;
    }

    @Override
    public Task createTask(Task task, Long userId){
        User user = userRepository.findById(userId).orElseThrow();
        task.setUser(user);
        return taskRepository.save(task);
    }

    @Override
    public List<Task> getTasksByUser(Long userId){
        return taskRepository.findByUserId(userId);
    }

    @Override
    public Task getTask(Long id){
        return taskRepository.findById(id).orElse(null);
    }

    @Override
    public void updateTask(Task task){
        taskRepository.save(task);
    }

    @Override
    public void deleteTask(Long id){
        taskRepository.deleteById(id);
    }
}
