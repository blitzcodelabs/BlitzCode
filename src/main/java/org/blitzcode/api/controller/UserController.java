package org.blitzcode.api.controller;

import org.blitzcode.api.model.Language;
import org.blitzcode.api.model.User;
import org.blitzcode.api.repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUserBaseLanguage(Long id, Language language) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()){
            user.get().setBaseLanguage(language);
            return userRepository.save(user.get());
        }
        throw new RuntimeException("User not found");
    }

    public User updateUserTargetLanguage(Long id, Language language) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()){
            user.get().setTargetLanguage(language);
            return userRepository.save(user.get());
        }
        throw new RuntimeException("User not found");
    }
}