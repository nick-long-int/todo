package com.emobile.springtodo.service;

import com.emobile.springtodo.dto.TaskDto;
import com.emobile.springtodo.mapper.TaskMapperImpl;
import com.emobile.springtodo.model.Task;
import com.emobile.springtodo.repo.TaskRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ToDoServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Spy
    private TaskMapperImpl taskMapper;

    @InjectMocks
    private ToDoService service;

    @Test
    @DisplayName("Создание задачи")
    void testAddTask() {
        TaskDto dto = new TaskDto();
        dto.setStatus("created");
        dto.setTitle("something title");
        dto.setDescription("something description");

        when(taskRepository.save(any(Task.class))).thenReturn(new Task());

        TaskDto result = service.addTask(dto);

        verify(taskRepository, times(1)).save(any(Task.class));
        assertNotNull(result);
    }

    @Test
    @DisplayName("Получение задачи по id")
    void testGetTaskById() {

        when(taskRepository.findById(anyString())).thenReturn(new Task());
        TaskDto result = service.getTaskById(anyString());

        verify(taskRepository, times(1)).findById(anyString());
        assertNotNull(result);
    }

    @Test
    @DisplayName("Получение списка задач")
    void testGetAllTasks(){

        when(taskRepository.findAll(anyInt(), anyInt())).thenReturn(new ArrayList<Task>());

        List<TaskDto> result = service.getAllTasks(anyInt(), anyInt());

        verify(taskRepository, times(1)).findAll(anyInt(), anyInt());
        assertNotNull(result);
    }

    @Test
    @DisplayName("Удаление задачи по id")
    void testDeleteTask() {
        service.deleteTaskById(anyString());
        verify(taskRepository, times(1)).deleteById(anyString());
    }

    @Test
    @DisplayName("Обновление задачи по id")
    void testUpdateTask() {
        TaskDto dto = new TaskDto();
        dto.setStatus("updated");
        dto.setTitle("something title");
        dto.setDescription("something description");

        Task task = new Task();
        task.setStatus("created");

        when(taskRepository.findById(anyString())).thenReturn(task);
        when(taskRepository.save(task)).thenReturn(task);

        dto = service.updateTask(anyString(), dto);

        verify(taskRepository, times(1)).findById(anyString());
        verify(taskRepository, times(1)).save(any(Task.class));
        assertEquals("updated", dto.getStatus());
    }

}