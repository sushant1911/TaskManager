package com.example.TaskManager.service;

import com.example.TaskManager.Repository.UserRepository;
import com.example.TaskManager.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserService  {
    private final UserRepository userRepository;
    private final RedisTemplate<String, User> template; // Update RedisTemplate to use User type

    public static final String HASH_KEY = "user";

    @Autowired
    public UserService(UserRepository userRepository, RedisTemplate<String, User> template) { // Inject RedisTemplate with User type
        this.userRepository = userRepository;
        this.template = template;
    }

    @Cacheable(key="#email", value="email")
    public User getUserByEmail(String email) {
        HashOperations<String, String, User> hashOps = template.opsForHash(); // Specify generic types for HashOperations

        User user = hashOps.get(HASH_KEY, email); // Use generic types for HashOperations

        if (user == null) {
            user = userRepository.findByEmail(email);
            if (user != null) {
                hashOps.put(HASH_KEY, email, user); // Use generic types for HashOperations
            }
        }
        return user;
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }
}
