package com.emobile.springtodo.controller;

import com.emobile.springtodo.dto.TaskDto;
import com.emobile.springtodo.service.ToDoService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
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
    public List<TaskDto> getAllTasks(
        @RequestParam(defaultValue = "20") @Min(1) @Max(100)
        int limit,
        @RequestParam(defaultValue = "0") @Min(0)int offset) {
        return service.getAllTasks(limit, offset);
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
