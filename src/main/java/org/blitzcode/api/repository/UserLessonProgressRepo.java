package org.blitzcode.api.repository;

import org.blitzcode.api.model.UserLessonProgress;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface to interact with the UserLessonProgress table.
 */
public interface UserLessonProgressRepo extends JpaRepository<UserLessonProgress, Long> {
}
