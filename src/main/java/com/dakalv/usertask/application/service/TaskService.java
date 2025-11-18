package com.dakalv.usertask.application.service;

import com.dakalv.usertask.application.dto.task.CreateTaskDTO;
import com.dakalv.usertask.application.dto.task.TaskDTO;
import java.util.List;

public interface TaskService {
    TaskDTO createTask(CreateTaskDTO task, Long userId);
    List<TaskDTO> getTasksByUser(Long userId);
}
