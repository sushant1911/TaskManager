package com.example.TaskManager.Controller;

import com.example.TaskManager.DTO.TaskManagerResponse;
import com.example.TaskManager.model.User;
import com.example.TaskManager.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<TaskManagerResponse> signUp(@RequestBody User user){
        return ResponseEntity.ok(authService.sigUp(user));
    }
    @PostMapping("/signin")
    public ResponseEntity<TaskManagerResponse> signIn(@RequestBody User signInRequest){
        return ResponseEntity.ok(authService.signIn(signInRequest));
    }
}
