package com.dakalv.usertask.domain.repository;

import com.dakalv.usertask.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
