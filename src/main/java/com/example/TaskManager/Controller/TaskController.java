package com.example.TaskManager.Controller;

import com.example.TaskManager.DTO.TaskManagerResponse;
import com.example.TaskManager.DTO.TaskResponseDTO;
import com.example.TaskManager.model.Task;
import com.example.TaskManager.model.User;
import com.example.TaskManager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    TaskService taskService;

    @GetMapping("/welcome")
    public String welcome() {
        return "welcome-task";
    }

    @PostMapping("/createTask")
    public ResponseEntity<Object> createTask(@RequestBody Task task) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        task.setUser(user);
        task.setCreatedAt(new Date());
        task.setUpdatedAt(new Date());
        final Task createdTask = taskService.createTask(task);
        return TaskManagerResponse.generateResponse("Successfully created Task", HttpStatus.CREATED, createdTask);
    }

    @GetMapping("/getAllTasks")
    public ResponseEntity<Object> getAllTasks() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        List<Task> tasks = taskService.getAllTasksByUserId(user.getId());

        // Create a list to hold task details without nested user objects
        List<TaskResponseDTO> taskResponseDTOs = new ArrayList<>();

        // Convert Task objects to TaskResponseDTO objects without nested user objects
        for (Task task : tasks) {
            TaskResponseDTO taskResponseDTO = new TaskResponseDTO();
            taskResponseDTO.setId(task.getId());
            taskResponseDTO.setTitle(task.getTitle());
            taskResponseDTO.setDescription(task.getDescription());
            taskResponseDTO.setDeadline(task.getDeadline());
            taskResponseDTO.setPriority(task.getPriority());
            taskResponseDTO.setStatus(task.getStatus());
            taskResponseDTO.setCreatedAt(task.getCreatedAt());
            taskResponseDTO.setUpdatedAt(task.getUpdatedAt());
            taskResponseDTO.setUserId(task.getUser().getId()); // Add only user ID, not the whole user object
            taskResponseDTOs.add(taskResponseDTO);
        }

        return TaskManagerResponse.generateResponse("Successfully retrieved all tasks", HttpStatus.OK, taskResponseDTOs);
    }

    @PutMapping("/updateTask/{taskId}")
    public ResponseEntity<Object> updateTask(@PathVariable Long taskId, @RequestBody Task updatedTask) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        updatedTask.setUser(user); // Update user information if necessary
        return taskService.updateTask(taskId, updatedTask);
    }

    @DeleteMapping("/deleteTask/{taskId}")
    public ResponseEntity<Object> deleteTask(@PathVariable Long taskId) {
        ResponseEntity<Object> responseEntity = taskService.deleteTask(taskId);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }
}
