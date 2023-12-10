package org.blitzcode.api.controller;

import org.blitzcode.api.model.Lesson;
import org.blitzcode.api.model.Question;
import org.blitzcode.api.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Used to interact with the Question model and repository.
 */
@Service
public class QuestionController {
    @Autowired
    private LessonRepository lessonRepository;

    /**
     * Retrieve a list of questions belonging to a lesson
     * @param id lesson id
     * @return list of questions
     */
    public List<Question> getQuestionsFromLessonID(Long id) {
        Optional<Lesson> lessonOptional = lessonRepository.findById(id);
        if(lessonOptional.isEmpty()){
            throw new RuntimeException("Lesson not found!");
        }
        return lessonOptional.get().getQuestions();
    }
}
