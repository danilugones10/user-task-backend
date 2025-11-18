package com.dakalv.usertask;

import com.dakalv.usertask.application.UserService;
import com.dakalv.usertask.domain.User;
import com.dakalv.usertask.infraestructure.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Testcontainers
@SpringBootTest
public class UserServiceTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("postgres")
            .withPassword("postgres");

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testCreateAndFindUser() {
        User user = new User();
        user.setName("Lucia");
        user.setEmail("lucia@mail.com");

        User saved = userService.createUser(user);

        assertThat(saved.getId()).isNotNull();

        User found = userService.getUser(saved.getId());
        assertThat(found.getEmail()).isEqualTo("lucia@mail.com");
    }
}
