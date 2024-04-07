package com.example.TaskManager.Controller;

import com.example.TaskManager.DTO.TaskManagerResponse;
import com.example.TaskManager.model.Task;
import com.example.TaskManager.model.User;
import com.example.TaskManager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    TaskService taskService;

    @Autowired
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
        final Task task1 = taskService.createTask(task);
        return TaskManagerResponse.generateResponse("Successfully created Task", HttpStatus.CREATED, task1);

    }
}
