package com.dakalv.usertask.application.dto.user;

import com.dakalv.usertask.application.dto.task.TaskDTO;

import java.util.ArrayList;
import java.util.List;

public class UserDTO {

    private Long id;
    private String name;
    private List<TaskDTO> tasks = new ArrayList<>(); // inicializada

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<TaskDTO> getTasks() { return tasks; }
    public void setTasks(List<TaskDTO> tasks) { this.tasks = tasks; }
}
