package com.vinay.taskmanager.controller;

import com.vinay.taskmanager.payload.TaskDto;
import com.vinay.taskmanager.repository.TaskRepository;
import com.vinay.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // save task
    @PostMapping("/{userId}/tasks")
    public ResponseEntity<TaskDto> saveTask(
            @PathVariable("userId") long userId,
            @RequestBody TaskDto taskDto
    ){
        return new ResponseEntity<>(taskService.assignTask(userId,taskDto), HttpStatus.CREATED);
    }

    // get all tasks
    @GetMapping("/{userId}/tasks")
    public ResponseEntity<List<TaskDto>> getTasks(
            @PathVariable("userId") long userId
    ){
        return new ResponseEntity<>(taskService.getAllTasks(userId),HttpStatus.OK);
    }

    // get individual task
    @GetMapping("/{userId}/tasks/{taskId}")
    public ResponseEntity<TaskDto> getTask(
            @PathVariable("userId") long userId,
            @PathVariable("taskId") long taskId
    ){
        return new ResponseEntity<>(taskService.getTask(userId,taskId),HttpStatus.OK);
    }

    // delete task
    @DeleteMapping("/{userId}/tasks/{taskId}")
    public ResponseEntity<String> deleteTask(
            @PathVariable("userId") long userId,
            @PathVariable("taskId") long taskId
    ){
        return new ResponseEntity<>(taskService.deleteTask(userId,taskId),HttpStatus.OK);
    }

}
