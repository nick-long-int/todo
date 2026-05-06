package com.emobile.springtodo.mapper;

import com.emobile.springtodo.dto.TaskDto;
import com.emobile.springtodo.model.Task;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
public interface TaskMapper {

    Task taskDtoToTask(TaskDto taskDto);

    TaskDto taskToTaskDto(Task task);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTask(TaskDto taskDto, @MappingTarget Task task);

}
