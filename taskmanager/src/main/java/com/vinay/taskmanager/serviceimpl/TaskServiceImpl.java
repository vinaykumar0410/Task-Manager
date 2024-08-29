package com.vinay.taskmanager.serviceimpl;

import com.vinay.taskmanager.entity.Task;
import com.vinay.taskmanager.entity.User;
import com.vinay.taskmanager.exception.APIException;
import com.vinay.taskmanager.exception.TaskNotFound;
import com.vinay.taskmanager.exception.UserNotFound;
import com.vinay.taskmanager.payload.TaskDto;
import com.vinay.taskmanager.repository.TaskRepository;
import com.vinay.taskmanager.repository.UserRepository;
import com.vinay.taskmanager.service.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public TaskDto assignTask(long userId, TaskDto taskDto) {
        Task task = modelMapper.map(taskDto,Task.class);
        User user = userRepository.findById(userId).get();
        task.setUser(user);
        Task savedTask = taskRepository.save(task);
        return modelMapper.map(savedTask,TaskDto.class);
    }

    @Override
    public List<TaskDto> getAllTasks(long userId) {
        userRepository.findById(userId).orElseThrow(
                ()->new UserNotFound("User Not Found")
        );
        List<Task> tasks = taskRepository.findAllByUserId(userId);
        return tasks.stream().map(task->modelMapper.map(task,TaskDto.class)).collect(Collectors.toList());
    }

    @Override
    public TaskDto getTask(long userId, long taskId) {
        User user = userRepository.findById(userId).orElseThrow(
                ()->new UserNotFound("User Not Found")
        );
        Task task = taskRepository.findById(taskId).orElseThrow(
                ()->new TaskNotFound("Task not found")
        );
        if(user.getId() != task.getUser().getId()){
            throw new APIException("This task doesn't belong to this user");
        }
        return modelMapper.map(task,TaskDto.class);
    }

    @Override
    public String deleteTask(long userId, long taskId) {
        User user = userRepository.findById(userId).orElseThrow(
                ()->new UserNotFound("User not found")
        );
        Task task = taskRepository.findById(taskId).orElseThrow(
                ()-> new TaskNotFound("Task not found")
        );
        if(user.getId() != task.getUser().getId()){
            throw new APIException("This task doesn't belong to this user");
        }
        taskRepository.deleteById(taskId);
        return "Task deleted";
    }
}
