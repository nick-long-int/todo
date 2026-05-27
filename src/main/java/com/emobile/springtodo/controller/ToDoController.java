package com.emobile.springtodo.controller;

import com.emobile.springtodo.dto.PageResponse;
import com.emobile.springtodo.dto.TaskDto;
import com.emobile.springtodo.service.ToDoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public TaskDto updateTask(@Valid @RequestBody TaskDto dto, @PathVariable Long id) {
        return service.updateTask(id, dto);
    }

    @Override
    public PageResponse getTasks(
        @RequestParam(defaultValue = "20") @Min(1) @Max(100)
        int size,
        @RequestParam(defaultValue = "0") @Min(0)int page) {
        return service.getTasks(page, size);
    }

    @Override
    public TaskDto getTaskById(@PathVariable Long id) {
        return service.getTaskById(id);
    }

    @Override
    public void deleteTaskById(@PathVariable Long id) {
        service.deleteTaskById(id);
    }
}
