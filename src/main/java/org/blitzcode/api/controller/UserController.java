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

    @PostMapping("/createUser")
    public User createUser(String email, String password) {
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(password); // Ideally, you should encrypt the password
        newUser.setAdmin(Boolean.FALSE);
        return userRepository.save(newUser);
    }
}