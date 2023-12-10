package org.blitzcode.api.repository;

import org.blitzcode.api.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface to interact with the Question table.
 */
public interface QuestionRepository extends JpaRepository<Question, Long> {
}