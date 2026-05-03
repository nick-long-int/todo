package com.emobile.springtodo.controller;

import com.emobile.springtodo.dto.PageResponse;
import com.emobile.springtodo.dto.TaskDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "Список задач", description = "CRUD для списка задач")
@RequestMapping("/api/v1/todo")
public interface ToDoApi {

    @Operation(summary = "Создать задачу")
    @PostMapping
    TaskDto addTask(@Valid @RequestBody TaskDto taskDto);

    @Operation(summary = "Обновить задачу")
    @PutMapping("/{id}")
    TaskDto updateTask(@Valid @RequestBody TaskDto taskDto,
                       @Parameter(description = "ID задачи" )
                       @PathVariable String id);

    @Operation(summary = "Получить список задач")
    @GetMapping
    PageResponse getAllTasks(
        @Parameter(description = "Максимум записей")
        @RequestParam(defaultValue = "20") @Min(1) @Max(100) int limit,
        @Parameter(description = "Смещение от начала выборки")
        @RequestParam(defaultValue = "0") @Min(0) int offset
    );

    @Operation(summary = "Получить задачу по id")
    @GetMapping("/{id}")
    TaskDto getTaskById(@PathVariable String id);

    @Operation(summary = "Удалить задачу")
    @DeleteMapping("/{id}")
    void deleteTaskById(@PathVariable String id);

}
