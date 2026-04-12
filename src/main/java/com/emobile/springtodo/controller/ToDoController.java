package com.emobile.springtodo.controller;

import com.emobile.springtodo.dto.TaskDto;
import com.emobile.springtodo.service.ToDoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todo")
@RequiredArgsConstructor
public class ToDoController {

    private final ToDoService service;

    @PostMapping
    public TaskDto addTask(@Valid @RequestBody TaskDto dto) {
        return service.addTask(dto);
    }

    @PutMapping("/{id}")
    public TaskDto updateTask(@PathVariable String id, @Valid @RequestBody TaskDto dto) {
        return service.updateTask(id, dto);
    }

    @GetMapping
    public List<TaskDto> getAllTasks() {
        return service.getAllTasks();
    }

    @GetMapping("/{id}")
    public TaskDto getTaskById(@PathVariable String id) {
        return service.getTaskById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteTaskById(@PathVariable String id) {
        service.deleteTaskById(id);
    }
}
