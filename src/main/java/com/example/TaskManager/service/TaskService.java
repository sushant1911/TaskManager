package com.example.TaskManager.service;

import com.example.TaskManager.DTO.TaskManagerResponse;
import com.example.TaskManager.model.Task;
import com.example.TaskManager.Repository.TaskRepository;
import com.example.TaskManager.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public ResponseEntity<Object> updateTask(Long taskId, Task updatedTask) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isPresent()) {
            Task existingTask = optionalTask.get();
            existingTask.setTitle(updatedTask.getTitle());
            existingTask.setDescription(updatedTask.getDescription());
            existingTask.setDeadline(updatedTask.getDeadline());
            existingTask.setPriority(updatedTask.getPriority());
            existingTask.setStatus(updatedTask.getStatus());
            existingTask.setUpdatedAt(new Date()); // Update the updatedAt timestamp
            Task savedTask = taskRepository.save(existingTask);
            return TaskManagerResponse.generateResponse("Task updated successfully",HttpStatus.OK, savedTask);
        } else {

            return TaskManagerResponse.generateResponse("User Not found",HttpStatus.NOT_FOUND, null);
        }
    }


    public List<Task> getAllTasksByUserId(Long userId) {
       return taskRepository.findTaskByUserId(userId);

    }

    public ResponseEntity<Object> deleteTask(Long taskId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            if (task.getUser().getId().equals(currentUser.getId())) {
                taskRepository.deleteById(taskId);
                return TaskManagerResponse.generateResponse("Task deleted successfully", HttpStatus.OK, null);
            } else {
                return TaskManagerResponse.generateResponse("You are not authorized to delete this task", HttpStatus.UNAUTHORIZED, null);
            }
        } else {
            return TaskManagerResponse.generateResponse("Task not found", HttpStatus.NOT_FOUND, null);
        }
    }

}
