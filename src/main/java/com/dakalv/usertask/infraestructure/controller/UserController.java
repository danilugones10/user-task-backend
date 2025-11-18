package com.dakalv.usertask.infraestructure.controller;

import com.dakalv.usertask.application.service.UserService;
import com.dakalv.usertask.application.dto.user.CreateUserDTO;
import com.dakalv.usertask.application.dto.user.UserDTO;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService service){
        this.userService = service;
    }

    @PostMapping
    public UserDTO createUser(@RequestBody CreateUserDTO dto){
        return userService.createUser(dto);
    }

    @GetMapping
    public List<UserDTO> getAllUsers(){
        return userService.getAllUsers();
    }
}
