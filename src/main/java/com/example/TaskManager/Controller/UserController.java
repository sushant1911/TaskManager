package com.example.TaskManager.Controller;

import com.example.TaskManager.DTO.TaskManagerResponse;
import com.example.TaskManager.DTO.UserDTO;
import com.example.TaskManager.model.User;
import com.example.TaskManager.service.UserService;
import com.example.TaskManager.utills.JWTUtils;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        final User userByName = userService.getUserByEmail(email);
        UserDTO userDTO=new UserDTO();
        userDTO.setName(userByName.getUsername());
        userDTO.setId(userByName.getId());
        userDTO.setEmail(userByName.getEmail());
        if (userDTO != null) {
         return TaskManagerResponse.generateResponse( "User found",HttpStatus.OK,userDTO);

        } else {
            return TaskManagerResponse.generateResponse ("User not found with name: " + email,HttpStatus.NOT_FOUND, null);

        }
    }


}
