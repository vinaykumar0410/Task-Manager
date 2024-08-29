package com.vinay.taskmanager.service;

import com.vinay.taskmanager.payload.TaskDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskService {

    TaskDto assignTask(long userId, TaskDto taskDto);

    List<TaskDto> getAllTasks(long userId);

    TaskDto getTask(long userId, long taskId);

    String deleteTask(long userId, long taskId);
}
