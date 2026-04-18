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
@RequiredArgsConstructor
public class ToDoController implements ToDoApi {

    private final ToDoService service;

    @Override
    public TaskDto addTask(@Valid @RequestBody TaskDto dto) {
        return service.addTask(dto);
    }

    @Override
    public TaskDto updateTask(@Valid @RequestBody TaskDto dto, @PathVariable String id) {
        return service.updateTask(id, dto);
    }

    @Override
    public List<TaskDto> getAllTasks() {
        return service.getAllTasks();
    }

    @Override
    public TaskDto getTaskById(@PathVariable String id) {
        return service.getTaskById(id);
    }

    @Override
    public void deleteTaskById(@PathVariable String id) {
        service.deleteTaskById(id);
    }
}
