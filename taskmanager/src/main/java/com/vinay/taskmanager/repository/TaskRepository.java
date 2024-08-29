package com.vinay.taskmanager.repository;

import com.vinay.taskmanager.entity.Task;
import com.vinay.taskmanager.payload.TaskDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Long> {

    List<Task> findAllByUserId(long userId);
}
