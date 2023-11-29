package org.blitzcode.api.controller;

import org.blitzcode.api.model.User;
import org.blitzcode.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/getAll")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping(value = "/createUser")
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }
}