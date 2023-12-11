package org.blitzcode.api.repository;

import org.blitzcode.api.model.User;
import org.blitzcode.api.model.UserLessonProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.blitzcode.api.model.Lesson;

import java.util.List;

/**
 * Interface to interact with the UserLessonProgress table.
 */
public interface UserLessonProgressRepo extends JpaRepository<UserLessonProgress, Long> {
    // Get UserLessonProgress by User
    List<UserLessonProgress> findByUser(User user);

    // Get UserLessonProgress by User and Lesson
    UserLessonProgress findByUserAndLesson(User user, Lesson lesson);
}
