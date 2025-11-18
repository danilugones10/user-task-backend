package com.dakalv.usertask.application.service;

import com.dakalv.usertask.application.dto.user.CreateUserDTO;
import com.dakalv.usertask.application.dto.user.UserDTO;
import com.dakalv.usertask.domain.model.User;

import java.util.List;

public interface UserService {
    UserDTO createUser(CreateUserDTO user);
    List<UserDTO> getAllUsers();
    User getUserById(Long id);
}
