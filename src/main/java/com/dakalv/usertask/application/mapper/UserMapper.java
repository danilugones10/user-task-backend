package com.dakalv.usertask.application.mapper;

import com.dakalv.usertask.application.dto.user.UserDTO;
import com.dakalv.usertask.domain.model.User;

import java.util.stream.Collectors;

public class UserMapper {

    public static UserDTO toDTO(User user) {

        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());

        dto.setTasks(
                user.getTasks()
                        .stream()
                        .map(TaskMapper::toDTO)
                        .collect(Collectors.toList())
        );

        return dto;
    }
}
