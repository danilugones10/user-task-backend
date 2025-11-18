package com.dakalv.usertask.application;

import com.dakalv.usertask.domain.Task;
import java.util.List;

public interface TaskService {
    Task createTask(Task task, Long userId);
    List<Task> getTasksByUser(Long userId);
    Task getTask(Long id);
    void updateTask(Task task);
    void deleteTask(Long id);
}
