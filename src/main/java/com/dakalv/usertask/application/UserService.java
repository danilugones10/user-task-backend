package com.dakalv.usertask.application;

import com.dakalv.usertask.domain.User;
import java.util.List;

public interface UserService {
    User createUser(User user);
    List<User> getAllUsers();
    User getUser(Long id);
    void deleteUser(Long id);
}

