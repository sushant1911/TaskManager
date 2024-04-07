package com.example.TaskManager.Controller;

import com.example.TaskManager.DTO.TaskManagerResponse;
import com.example.TaskManager.model.User;
import com.example.TaskManager.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@EnableCaching
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody User user){

        return TaskManagerResponse.generateResponse("User Created",HttpStatus.OK,authService.signUp(user));
    }
    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody User signInRequest){
        return TaskManagerResponse.generateResponse("User Successfully Log-in",HttpStatus.OK,authService.signIn(signInRequest));
    }
}
