package com.example.TaskManager.service;

import com.example.TaskManager.DTO.TaskManagerResponse;
import com.example.TaskManager.Repository.TaskRepository;
import com.example.TaskManager.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TaskService {


    TaskRepository taskRepository;
    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(Task task)
    {
       return taskRepository.save(task);

    }
}
