package com.projects.JustDoIt.mappers.impl;

import com.projects.JustDoIt.mappers.Mapper;
import com.projects.JustDoIt.model.dto.TaskDto;
import com.projects.JustDoIt.model.enitities.Task;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TaskMapperImpl implements Mapper<Task, TaskDto> {

    private ModelMapper modelMapper;

    public TaskMapperImpl(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    @Override
    public TaskDto mapTo(Task task) {
        return modelMapper.map(task, TaskDto.class);
    }

    @Override
    public Task mapFrom(TaskDto taskDto) {
        return modelMapper.map(taskDto, Task.class);
    }
}
