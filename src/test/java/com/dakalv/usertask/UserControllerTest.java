package com.dakalv.usertask;

import com.dakalv.usertask.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("postgres")
            .withPassword("postgres");

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testCreateAndGetUser() {
        User user = new User();
        user.setName("Mario");
        user.setEmail("mario@mail.com");

        ResponseEntity<User> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/users", user, User.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        User created = response.getBody();
        assertThat(created).isNotNull();
        assertThat(created.getId()).isNotNull();

        ResponseEntity<User> getResponse = restTemplate.getForEntity(
                "http://localhost:" + port + "/users/" + created.getId(), User.class);

        assertThat(getResponse.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(getResponse.getBody().getEmail()).isEqualTo("mario@mail.com");
    }
}
