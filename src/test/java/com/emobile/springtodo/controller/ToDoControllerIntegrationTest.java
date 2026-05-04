package com.emobile.springtodo.controller;

import com.emobile.springtodo.dto.PageResponse;
import com.emobile.springtodo.dto.TaskDto;
import com.emobile.springtodo.model.TaskStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class ToDoControllerIntegrationTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Container
    static PostgreSQLContainer<?> postgres =
        new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test")
            .withReuse(false);

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.driver-class-name", postgres::getDriverClassName);
        registry.add("spring.cache.type", () -> "none");
    }

    @LocalServerPort
    private Integer port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl() {
        return "http://localhost:" + port + "/api/v1/todo";
    }


    @Test
    @Sql("/sql/tasks_test.sql")
    @DisplayName("Создание задачи")
    void testAddTaskSuccess() throws IOException {
        TaskDto taskDto = new TaskDto();
        taskDto.setTitle("New Title");
        taskDto.setDescription("New Description");

        ResponseEntity<TaskDto> result = restTemplate.postForEntity(baseUrl(),
            taskDto, TaskDto.class);

        taskDto = result.getBody();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(taskDto);
        assertNotNull(taskDto.getId());
        assertEquals("New Title", taskDto.getTitle());
        assertEquals("New Description", taskDto.getDescription());
        assertEquals("NEW", taskDto.getStatus());
    }

    @Test
    @Sql("/sql/tasks_test.sql")
    @DisplayName("Получение задачи по id")
    void testGetTaskByIdSuccess() throws IOException {
        String id = "1";

        TaskDto expectedDto = new TaskDto();
        expectedDto.setId(id);
        expectedDto.setTitle("Title 1");
        expectedDto.setDescription("Description 1");
        expectedDto.setStatus(TaskStatus.NEW.name());

        ResponseEntity<TaskDto> result = restTemplate.getForEntity(baseUrl() + "/" + id, TaskDto.class);

        assertEquals(HttpStatus.OK, result.getStatusCode());

        TaskDto actualDto = result.getBody();

        assertEquals(expectedDto.getId(), actualDto.getId());
        assertEquals(expectedDto.getTitle(), actualDto.getTitle());
        assertEquals(expectedDto.getDescription(), actualDto.getDescription());
        assertEquals(expectedDto.getStatus(), actualDto.getStatus());
    }

    @Test
    @Sql("/sql/tasks_test.sql")
    @DisplayName("Получение задачи по несуществующему id")
    void testGetTaskByIdWithThrowNotFoundException() throws IOException {
        String id = "unreal id";

        ResponseEntity<TaskDto> result = restTemplate.getForEntity(baseUrl() + "/" + id, TaskDto.class);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    @Sql("/sql/tasks_test.sql")
    @DisplayName("Удаление задачи по id")
    void testDeleteTaskSuccess() throws IOException {
        String id = "1";

        ResponseEntity<TaskDto> result = restTemplate.getForEntity(baseUrl() + "/" + id, TaskDto.class);

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    @Sql("/sql/tasks_test.sql")
    @DisplayName("Получение списка задач из 10 элементов")
    void testGetAllTasksSuccess() throws IOException {
        int limit = 10;
        int offset = 0;

        ResponseEntity<PageResponse> result = restTemplate.exchange(baseUrl() + "?limit={limit}&offset={offset}",
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<>() {
            },
            limit, offset);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        PageResponse page = result.getBody();
        assertEquals(limit, page.getTasks().size());
    }

    @Test
    @Sql("/sql/tasks_test.sql")
    @DisplayName("Обновление задачи")
    void testUpdateTaskSuccess() throws IOException {
        String id = "5";
        TaskDto expectedDto = new TaskDto();
        expectedDto.setTitle("New Title");
        expectedDto.setDescription("New Description");
        expectedDto.setStatus(TaskStatus.BLOCKED.name());

        ResponseEntity<TaskDto> result = restTemplate.exchange(
            baseUrl() + "/" + id,
            HttpMethod.PUT,
            new HttpEntity<>(expectedDto),
            TaskDto.class
        );

        assertEquals(HttpStatus.OK, result.getStatusCode());
        TaskDto actualDto = result.getBody();
        assertEquals(id, actualDto.getId());
        assertEquals("New Title", actualDto.getTitle());
        assertEquals("New Description", actualDto.getDescription());
        assertEquals("BLOCKED", actualDto.getStatus());
    }

    @Test
    @Sql("/sql/tasks_test.sql")
    @DisplayName("Обновление задачи по несуществующему id")
    void testUpdateTaskWithThrowNotFoundException() throws IOException {
        String id = "unreal id";

        ResponseEntity<TaskDto> result = restTemplate.getForEntity(baseUrl() + "/" + id, TaskDto.class);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

}
