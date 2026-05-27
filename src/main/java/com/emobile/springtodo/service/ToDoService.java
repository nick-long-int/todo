package com.emobile.springtodo.service;

import com.emobile.springtodo.dto.PageResponse;
import com.emobile.springtodo.dto.TaskDto;
import com.emobile.springtodo.exception.NotFoundException;
import com.emobile.springtodo.mapper.TaskMapper;
import com.emobile.springtodo.model.Task;
import com.emobile.springtodo.model.TaskStatus;
import com.emobile.springtodo.repo.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "tasks", cacheManager = "cacheManager")
public class ToDoService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @CachePut(key = "#result.id")
    @Transactional
    public TaskDto addTask(TaskDto taskDto) {
        Task task = taskMapper.taskDtoToTask(taskDto);
        task.setStatus(TaskStatus.NEW);
        return taskMapper.taskToTaskDto(taskRepository.save(task));
    }

    @Transactional(readOnly = true)
    public PageResponse getTasks(int page, int size) {
        List<TaskDto> tasks = taskRepository.findTasksPageable(page, size)
            .stream()
            .map(taskMapper::taskToTaskDto)
            .toList();
        long total = taskRepository.count();
        return PageResponse.builder()
            .tasks(tasks)
            .page(page)
            .size(size)
            .totalElements(total)
            .totalPages((int) Math.ceil((double)total / size))
            .build();
    }

    @Cacheable(key = "#id")
    @Transactional(readOnly = true)
    public TaskDto getTaskById(Long id) {
        Task task = taskRepository.findById(id);
        if (Objects.isNull(task)) {
            throw new NotFoundException("Task with id " + id + " not found");
        }
        return taskMapper.taskToTaskDto(task);
    }

    @CacheEvict(key = "#id")
    @Transactional
    public void deleteTaskById(Long id) {
        taskRepository.deleteById(id);
    }

    @CachePut(key = "#id")
    @Transactional
    public TaskDto updateTask(Long id, TaskDto taskDto) {
        Task task = taskRepository.findById(id);
        if (Objects.isNull(task)) {
            throw new NotFoundException("Task with id " + id + " not found");
        }
        task.setStatus(TaskStatus.UPDATED);
        taskMapper.updateTask(taskDto, task);
        return taskMapper.taskToTaskDto(taskRepository.save(task));
    }
}
