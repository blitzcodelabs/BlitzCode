package org.blitzcode.api.controller;

import org.blitzcode.api.model.Language;
import org.blitzcode.api.model.Lesson;
import org.blitzcode.api.model.User;
import org.blitzcode.api.model.UserLessonProgress;
import org.blitzcode.api.repository.LessonRepository;
import org.blitzcode.api.repository.UserLessonProgressRepo;
import org.blitzcode.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Used to interact with the User model and interface.
 */
@Service
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserLessonProgressRepo userLessonProgressRepo;
    @Autowired
    private LessonRepository lessonRepository;

    /**
     * Get all users. Should only be used for debugging.
     * @return a list of users
     */
    private List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Checks if a user already exists, then saves the user to the database.
     * @param user User object to save to database.
     * @return created user
     */
    public User createUser(User user) {
        if (userRepository.findById(user.getId()) != null) {
            throw new RuntimeException("User already exists in database!");
        }
        return userRepository.save(user);
    }

    /**
     * Converts the JWT Auth Token to a user id.
     * @param token JWT Token
     * @return User from database
     */
    public User getUserByID(JwtAuthenticationToken token) {
        return userRepository.findById(token.getName());
    }

    /**
     * Fetches a User directly with their ID
     * @param id User ID
     * @return User from database
     */
    public User getUserByID(String id) {
        return userRepository.findById(id);
    }

    /**
     * Updates a User's base language in the DB
     * @param id User ID
     * @param language new base language
     * @return User
     */
    public User updateUserBaseLanguage(String id, Language language) {
        User user = userRepository.findById(id);
        user.setBaseLanguage(language);
        return userRepository.save(user);
    }

    /**
     * Updates a User's target language in the DB
     * @param id User ID
     * @param language new target language
     * @return User
     */
    public User updateUserTargetLanguage(String id, Language language) {
        User user = userRepository.findById(id);
        user.setTargetLanguage(language);
        return userRepository.save(user);
    }

    /**
     * Increments a user's progress for the given lesson.
     * @param lessonID lesson ID
     * @param userID user ID
     * @return UserLessonProgress with the user, lesson and completed points
     */
    public UserLessonProgress incrementUserProgress(long lessonID, String userID) {
        Optional<Lesson> lesson = lessonRepository.findById(lessonID);
        if (lesson.isEmpty()) {
            throw new RuntimeException("Could not find lesson by id " + lessonID);
        }
        User user = userRepository.findById(userID);
        if (user == null) {
            throw new RuntimeException("Could not find user by id " + userID);
        }

        UserLessonProgress userLessonProgress = getProgressByUserAndLesson(user, lesson.get());
        if (userLessonProgress != null && !userLessonProgress.getCompletedPoints().equals(lesson.get().getSectionsTotal())) {
            userLessonProgress.setCompletedPoints(userLessonProgress.getCompletedPoints() + 1);
            return userLessonProgressRepo.save(userLessonProgress);
        }else if(userLessonProgress != null){
            return userLessonProgress;
        }else{
            UserLessonProgress newProgress = new UserLessonProgress();
            newProgress.setUser(user);
            newProgress.setLesson(lesson.get());
            newProgress.setCompletedPoints(1);
            return addUserProgress(newProgress);
        }
    }

    /**
     * Creates a new UserLessonProgress in the Database.
     * @param newProgress UserLessonProgress to be saved
     * @return saved UserLessonProgress
     */
    public UserLessonProgress addUserProgress(UserLessonProgress newProgress) {
        newProgress.getUser().getProgressList().add(newProgress);
        return userLessonProgressRepo.save(newProgress);
    }

    /**
     * Deletes a user from the database. Used alongside deleting the User form Firebase
     * @param user User to be deleted
     */
    public void deleteUser(User user){
        for(UserLessonProgress usp: user.getProgressList()){
            deleteUserLessonProgress(usp);
        }
        userRepository.delete(user);
    }

    /**
     * Deletes a UserLessonProgress entry from the Database and all relevant tables
     * @param usp UserLessonProgress to be deleted
     */
    public void deleteUserLessonProgress(UserLessonProgress usp){
        usp.getUser().getProgressList().remove(usp);
        usp.getLesson().getUserProgressList().remove(usp);
        userLessonProgressRepo.delete(usp);
    }

    public List<UserLessonProgress> getAllProgressByUser(User user){
        return userLessonProgressRepo.findByUser(user);
    }

    /**
     * Get UserLessonProgress by User and Lesson
     * @param user User
     * @param lesson Lesson
     * @return UserLessonProgress
     */
    public UserLessonProgress getProgressByUserAndLesson(User user, Lesson lesson){
        return userLessonProgressRepo.findByUserAndLesson(user, lesson);
    }
}