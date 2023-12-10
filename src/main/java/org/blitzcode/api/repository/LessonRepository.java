package org.blitzcode.api.repository;

import org.blitzcode.api.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Interface to interact with the Lesson table. findLessonByName() is used to fetch a lesson by name.
 */
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    Optional<Lesson> findLessonByName(String name);
}