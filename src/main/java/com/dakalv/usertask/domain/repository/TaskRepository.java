package com.dakalv.usertask.domain.repository;

import com.dakalv.usertask.domain.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
