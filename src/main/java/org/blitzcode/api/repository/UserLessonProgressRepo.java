package org.blitzcode.api.repository;

import org.blitzcode.api.model.User;
import org.blitzcode.api.model.UserLessonProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Interface to interact with the UserLessonProgress table.
 */
public interface UserLessonProgressRepo extends JpaRepository<UserLessonProgress, Long> {
    List<UserLessonProgress> findByUser(User user);
}
