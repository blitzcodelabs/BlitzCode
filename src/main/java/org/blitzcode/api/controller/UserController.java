package org.blitzcode.api.controller;

import org.blitzcode.api.model.Language;
import org.blitzcode.api.model.Lesson;
import org.blitzcode.api.model.User;
import org.blitzcode.api.model.UserLessonProgress;
import org.blitzcode.api.repository.LessonRepository;
import org.blitzcode.api.repository.UserLessonProgressRepo;
import org.blitzcode.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserLessonProgressRepo userLessonProgressRepo;
    @Autowired
    private LessonRepository lessonRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        if(userRepository.findById(user.getId()) != null ){
            throw new RuntimeException("User already exists in database!");
        }
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

    public UserLessonProgress incrementUserProgress(long lessonID, String userID){
        Optional<Lesson> lesson = lessonRepository.findById(lessonID);
        if(lesson.isEmpty()){
            throw new RuntimeException("Could not find lesson by id " + lessonID);
        }
        User user = userRepository.findById(userID);
        if(user == null){
            throw new RuntimeException("Could not find user by id " + userID);
        }

        ArrayList<UserLessonProgress> userProgress = (ArrayList<UserLessonProgress>) userRepository.findById(userID).getProgressList();
        for(UserLessonProgress i: userProgress){
            if(i.getId() == lessonID){
                if (!i.getCompletedPoints().equals(lesson.get().getPoints())){
                    i.setCompletedPoints(i.getCompletedPoints() + 1);
                    return userLessonProgressRepo.save(i);
                }
            }
        }
        UserLessonProgress newProgress = new UserLessonProgress();
        newProgress.setUser(user);
        newProgress.setLesson(lesson.get());
        newProgress.setCompletedPoints(1);
        return addUserProgress(newProgress);
    }

    public UserLessonProgress addUserProgress(UserLessonProgress newProgress){
        newProgress.getUser().getProgressList().add(newProgress);
        return userLessonProgressRepo.save(newProgress);
    }
}