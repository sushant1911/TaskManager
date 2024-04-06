package com.example.TaskManager.service;

import com.example.TaskManager.Repository.UserRepository;
import com.example.TaskManager.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Transactional
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public User addUser(User user)
    {
       return userRepository.save(user);
    }
}
