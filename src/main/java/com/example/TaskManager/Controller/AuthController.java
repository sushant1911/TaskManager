package com.example.TaskManager.Controller;

import com.example.TaskManager.DTO.TaskManagerResponse;
import com.example.TaskManager.model.User;
import com.example.TaskManager.service.AuthService;
import com.example.TaskManager.utills.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@EnableCaching
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    JWTUtils jwtUtils;
    @GetMapping("/welcome")
    public String welcome()
    {
        return "welcome";
    }
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody User user){

        return TaskManagerResponse.generateResponse("User Created",HttpStatus.OK,authService.signUp(user));
    }
    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody User signInRequest){
        return TaskManagerResponse.generateResponse("User Successfully Log-in",HttpStatus.OK,authService.signIn(signInRequest));
    }
    @PostMapping("/tokenIsValid")
    public ResponseEntity<?> tokenIsValid(@RequestBody String token) {
        // Retrieve authentication information
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) authentication.getPrincipal();

            // Check if the token is valid for the authenticated user
            final boolean validToken = jwtUtils.isValidToken(token, user);

            // Generate response based on token validity
            if (validToken) {
                return TaskManagerResponse.generateResponse("valid token", HttpStatus.OK, true);
            } else {
                return TaskManagerResponse.generateResponse("invalid token", HttpStatus.NOT_FOUND, null);
            }
        }
}
