package com.dakalv.usertask;

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
public class UserControllerTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("postgres")
            .withPassword("postgres")
            .withReuse(false);

    // Configurar datasource de Spring Boot con Testcontainers
    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testCreateAndListUsers() {
        // 1️⃣ Crear usuario
        CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.setName("Mario");

        ResponseEntity<UserDTO> createResponse = restTemplate.postForEntity(
                "/users",
                createUserDTO,
                UserDTO.class
        );

        assertThat(createResponse.getStatusCode().is2xxSuccessful()).isTrue();
        UserDTO created = createResponse.getBody();
        assertThat(created).isNotNull();
        assertThat(created.getId()).isNotNull();
        assertThat(created.getName()).isEqualTo("Mario");
        assertThat(created.getTasks()).isNotNull();
        assertThat(created.getTasks().size()).isEqualTo(0); // lista vacía

        // 2️⃣ Listar usuarios
        ResponseEntity<UserDTO[]> listResponse = restTemplate.getForEntity("/users", UserDTO[].class);
        assertThat(listResponse.getStatusCode().is2xxSuccessful()).isTrue();
        UserDTO[] users = listResponse.getBody();
        assertThat(users).isNotNull();
        assertThat(users.length).isGreaterThan(0);

        // Verificar que el usuario creado está en la lista
        boolean found = false;
        for (UserDTO user : users) {
            if (user.getId().equals(created.getId()) && user.getName().equals("Mario")) {
                found = true;
                break;
            }
        }
        assertThat(found).isTrue();
    }
}
