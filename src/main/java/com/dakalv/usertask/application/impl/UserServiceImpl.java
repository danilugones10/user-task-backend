package com.dakalv.usertask.application.impl;

import com.dakalv.usertask.application.UserService;
import com.dakalv.usertask.domain.User;
import com.dakalv.usertask.infraestructure.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository repo){
        this.userRepository = repo;
    }

    @Override
    public User createUser(User user){
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @Override
    public User getUser(Long id){
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
}

