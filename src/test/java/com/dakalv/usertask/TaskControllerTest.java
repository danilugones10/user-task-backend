package com.dakalv.usertask;

import com.dakalv.usertask.application.dto.task.CreateTaskDTO;
import com.dakalv.usertask.application.dto.task.TaskDTO;
import com.dakalv.usertask.application.dto.user.CreateUserDTO;
import com.dakalv.usertask.application.dto.user.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskControllerTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("postgres")
            .withPassword("postgres")
            .withReuse(false);

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testCreateAndListTasks() {
        // 1️⃣ Crear usuario
        CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.setName("Ana");
        ResponseEntity<UserDTO> userResp = restTemplate.postForEntity("/users", createUserDTO, UserDTO.class);
        UserDTO user = userResp.getBody();
        assertThat(user).isNotNull();

        Long userId = user.getId();

        // 2️⃣ Crear tarea para ese usuario
        CreateTaskDTO createTaskDTO = new CreateTaskDTO();
        createTaskDTO.setTitle("Comprar pan");

        ResponseEntity<TaskDTO> taskResp = restTemplate.postForEntity(
                "/tasks/user/" + userId,
                createTaskDTO,
                TaskDTO.class
        );

        assertThat(taskResp.getStatusCode().is2xxSuccessful()).isTrue();
        TaskDTO task = taskResp.getBody();
        assertThat(task).isNotNull();
        assertThat(task.getTitle()).isEqualTo("Comprar pan");

        // 3️⃣ Listar tareas del usuario
        ResponseEntity<TaskDTO[]> listResp = restTemplate.getForEntity(
                "/tasks/user/" + userId,
                TaskDTO[].class
        );

        assertThat(listResp.getStatusCode().is2xxSuccessful()).isTrue();
        TaskDTO[] tasks = listResp.getBody();
        assertThat(tasks).isNotNull();
        assertThat(tasks.length).isGreaterThan(0);
        assertThat(tasks[0].getTitle()).isEqualTo("Comprar pan");
    }
}