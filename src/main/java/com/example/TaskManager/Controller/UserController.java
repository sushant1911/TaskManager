package com.example.TaskManager.Controller;

import com.example.TaskManager.model.User;
import com.example.TaskManager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;
    @GetMapping("/welcome")
    public String welcome()
    {
        return "welcome";
    }
    @GetMapping("/getUser/{email}")
    public ResponseEntity<?> getUserByName(@PathVariable String email)
    {
        System.out.println("find");
        final User userByName = userService.getUserByEmail(email);
        if (userByName != null) {
            return ResponseEntity.ok(userByName);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with name: " + email);
        }
    }
}
