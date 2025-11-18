package com.dakalv.usertask.application.mapper;

import com.dakalv.usertask.application.dto.task.TaskDTO;
import com.dakalv.usertask.domain.model.Task;

public class TaskMapper {

    public static TaskDTO toDTO(Task task) {

        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());

        return dto;
    }
}
