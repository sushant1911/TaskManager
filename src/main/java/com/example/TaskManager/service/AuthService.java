package com.example.TaskManager.service;

import com.example.TaskManager.DTO.TaskManagerResponse;
import com.example.TaskManager.Exception.UserNotFoundException;
import com.example.TaskManager.model.User;
import com.example.TaskManager.utills.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserService userService;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    public TaskManagerResponse sigUp(User request) {
        TaskManagerResponse response = new TaskManagerResponse();
        try {
            User tempUser = new User();
            tempUser.setUsername(request.getUsername());
            tempUser.setEmail(request.getEmail());
            tempUser.setPassword(passwordEncoder.encode(request.getPassword()));
            if (request.getRole() == null)
                tempUser.setRole("user");
            else
                tempUser.setRole(request.getRole());
            final User saveUser = userService.addUser(tempUser);
            if (saveUser != null && saveUser.getId() > 0) {
                response.setData(saveUser);
                response.setStatusCode(200);
                response.setMessage("User Saved Successfully");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }
    public TaskManagerResponse signIn(User user){
        TaskManagerResponse response = new TaskManagerResponse();

        try {
            // authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
            User tempUser =  userService.getUserByEmail(user.getEmail());
            System.out.println("USER IS: "+ tempUser);
            if(tempUser.getEmail()!=null){
              String jwt = jwtUtils.generateToken(tempUser);
            response.setStatusCode(200);
            response.setData(jwt);
            response.setMessage("Successfully Signed In");}
            else throw new UserNotFoundException("User not found");
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

}
