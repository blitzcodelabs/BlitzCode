package org.blitzcode.api.repository;

import org.blitzcode.api.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    Optional<Lesson> findLessonByName(String name);
}