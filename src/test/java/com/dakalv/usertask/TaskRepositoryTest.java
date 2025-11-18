package com.dakalv.usertask;

import com.dakalv.usertask.domain.Task;
import com.dakalv.usertask.domain.User;
import com.dakalv.usertask.infraestructure.repository.TaskRepository;
import com.dakalv.usertask.infraestructure.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
public class TaskRepositoryTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("postgres")
            .withPassword("postgres");

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveTask() {
        User user = new User();
        user.setName("Ana");
        user.setEmail("ana@mail.com");
        userRepository.save(user);

        Task task = new Task();
        task.setTitle("Estudiar Spring");
        task.setUser(user);

        Task saved = taskRepository.save(task);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getUser().getName()).isEqualTo("Ana");
    }

    @Test
    void testFindByUserId() {
        User user = new User();
        user.setName("Carlos");
        user.setEmail("carlos@mail.com");
        userRepository.save(user);

        Task task1 = new Task();
        task1.setTitle("Tarea 1");
        task1.setUser(user);

        Task task2 = new Task();
        task2.setTitle("Tarea 2");
        task2.setUser(user);

        taskRepository.save(task1);
        taskRepository.save(task2);

        List<Task> tasks = taskRepository.findByUserId(user.getId());
        assertThat(tasks).hasSize(2);
    }
}
