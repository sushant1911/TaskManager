package com.example.TaskManager.service;

import com.example.TaskManager.Repository.UserRepository;
import com.example.TaskManager.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.stereotype.Service;

@Service
@EnableCaching
public class UserService  {
    private final UserRepository userRepository;
    public static final String HASH_KEY = "user";
    @Autowired
    private RedisTemplate template;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Cacheable(key="#email", value="useremail")
    public User getUserByEmail(String email) {
        User user = (User) template.opsForHash().get(HASH_KEY, email);
        if (user == null) {
            user = userRepository.findByEmail(email);
            if (user != null) {
                template.opsForHash().put(HASH_KEY, email, user);
            }
        }
        return user;
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

}
