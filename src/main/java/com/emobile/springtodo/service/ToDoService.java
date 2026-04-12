package com.emobile.springtodo.service;

import com.emobile.springtodo.dto.TaskDto;
import com.emobile.springtodo.mapper.TaskMapper;
import com.emobile.springtodo.model.Task;
import com.emobile.springtodo.repo.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ToDoService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Transactional
    public TaskDto addTask(TaskDto taskDto) {
        Task task = taskMapper.taskDtoToTask(taskDto);
        return taskMapper.taskToTaskDto(taskRepository.save(task));
    }

    @Transactional(readOnly = true)
    public List<TaskDto> getAllTasks() {
        return taskRepository.findAll()
            .stream()
            .map(taskMapper::taskToTaskDto)
            .toList();
    }

    @Transactional(readOnly = true)
    public TaskDto getTaskById(String id) {
        Task task = taskRepository.findById(id);
        return taskMapper.taskToTaskDto(task);
    }

    @Transactional
    public void deleteTaskById(String id) {
        taskRepository.deleteById(id);
    }

    @Transactional
    public TaskDto updateTask(String id, TaskDto taskDto) {
        Task task = taskRepository.findById(id);
        taskMapper.updateTask(taskDto, task);
        return taskMapper.taskToTaskDto(taskRepository.save(task));
    }
}
