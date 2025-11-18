package com.dakalv.usertask.application.service.impl;

import com.dakalv.usertask.application.dto.task.CreateTaskDTO;
import com.dakalv.usertask.application.dto.task.TaskDTO;
import com.dakalv.usertask.application.mapper.TaskMapper;
import com.dakalv.usertask.application.service.TaskService;
import com.dakalv.usertask.application.service.UserService;
import com.dakalv.usertask.domain.model.Task;
import com.dakalv.usertask.domain.model.User;
import com.dakalv.usertask.domain.repository.TaskRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;


    public TaskServiceImpl(TaskRepository taskRepository, UserService userService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
    }


    // Crear tarea para un usuario existente
    public TaskDTO createTask(CreateTaskDTO dto, Long id) {
        User user = userService.getUserById(id);

        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setUser(user);

        taskRepository.save(task);

        return TaskMapper.toDTO(task);
    }

    // Listar todas las tareas
    public List<TaskDTO> getTasksByUser(Long userId) {
        return taskRepository.findAll()
                .stream()
                .filter(task -> task.getUser().getId().equals(userId))
                .map(TaskMapper::toDTO)
                .collect(Collectors.toList());
    }
}
