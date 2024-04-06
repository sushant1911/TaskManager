package com.example.TaskManager.Controller;

import com.example.TaskManager.DTO.TaskManagerResponse;
import com.example.TaskManager.model.User;
import com.example.TaskManager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@EnableCaching
public class UserController {

    @Autowired
    UserService userService;
    @GetMapping("/welcome")
    public String welcome()
    {
        return "welcome";
    }


    @GetMapping("/getUser/{email}")
    public ResponseEntity<TaskManagerResponse> getUserByEmail(@PathVariable String email) {
        final User userByName = userService.getUserByEmail(email);
        if (userByName != null) {
            TaskManagerResponse response = new TaskManagerResponse(HttpStatus.OK.value(), "User found", userByName);
            return ResponseEntity.ok(response);
        } else {
            TaskManagerResponse response = new TaskManagerResponse(HttpStatus.NOT_FOUND.value(), "User not found with name: " + email, null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
