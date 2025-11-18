package com.dakalv.usertask.application.service.impl;

import com.dakalv.usertask.application.dto.user.CreateUserDTO;
import com.dakalv.usertask.application.dto.user.UserDTO;
import com.dakalv.usertask.application.mapper.UserMapper;
import com.dakalv.usertask.application.service.UserService;
import com.dakalv.usertask.domain.model.User;
import com.dakalv.usertask.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository repo){
        this.userRepository = repo;
    }

    // Crear usuario
    public UserDTO createUser(CreateUserDTO dto) {
        User user = new User();
        user.setName(dto.getName());

        userRepository.save(user);

        return UserMapper.toDTO(user);
    }

    // Listar todos los usuarios
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Obtener usuario por ID (lo necesitará TaskService)
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found with id " + id)
                );
    }
}
