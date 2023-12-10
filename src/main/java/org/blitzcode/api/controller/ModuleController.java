package org.blitzcode.api.controller;

import org.blitzcode.api.model.Lesson;
import org.blitzcode.api.model.Module;
import org.blitzcode.api.model.Question;
import org.blitzcode.api.repository.LessonRepository;
import org.blitzcode.api.repository.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * ModuleController is a wrapper class for the Module and Lesson models and repositories.
 * Most of its functions are basic interactions with the JPA database interface.
 */
@Service
public class ModuleController {
    @Autowired
    private ModuleRepository moduleRepository;
    @Autowired
    private LessonRepository lessonRepository;

    /**
     * Retrieves all modules from the repository.
     *
     * @return a list of all modules.
     */
    public List<Module> getAllModules() {
        return moduleRepository.findAll();
    }

    /**
     * Creates a new module, ensuring that each module has at least one lesson
     * and each lesson has at least one question with at least one wrong option.
     *
     * @param module the module to be created.
     * @return the saved module.
     * @throws RuntimeException if the module or its lessons do not meet the specified criteria.
     */
    public Module createModule(Module module) {
        if (module.getLessons().isEmpty()){
            throw new RuntimeException("Modules should contain at least one lesson!");
        }else{
            for (Lesson i : module.getLessons()){
                if (i.getQuestions().isEmpty()){
                    throw new RuntimeException("Lessons should contain at least one question!");
                }else{
                    for(Question q: i.getQuestions()){
                        if (q.getWrongOptions().isEmpty()){
                            throw new RuntimeException("Questions should contain at least one wrong option!");
                        }
                    }
                }
            }
        }
        return moduleRepository.save(module);
    }

    /**
     * Retrieves all lessons associated with a specific module ID.
     *
     * @param moduleID the ID of the module.
     * @return a list of lessons from the specified module.
     * @throws RuntimeException if no module with the given ID is found.
     */
    public List<Lesson> getLessonsFromModuleID(long moduleID){
        Optional<Module> moduleOptional = moduleRepository.findById(moduleID);
        if(moduleOptional.isEmpty()){
            throw new RuntimeException("");
        }

        return moduleOptional.get().getLessons();
    }

    /**
     * Retrieves all questions associated with a specific lesson ID.
     *
     * @param lessonID the ID of the lesson.
     * @return a list of questions from the specified lesson.
     * @throws RuntimeException if no lesson with the given ID is found.
     */
    public List<Question> getQuestionsFromLessonID(long lessonID){
        Optional<Lesson> lessonOptional = lessonRepository.findById(lessonID);
        if(lessonOptional.isEmpty()){
            throw new RuntimeException("Lesson not found with ID " + lessonID);
        }
        return lessonOptional.get().getQuestions();
    }

    /**
     * Finds a module by its name.
     *
     * @param name the name of the module.
     * @return the module with the specified name, or null if not found.
     */
    public Module findModuleByName(String name){
        Optional<Module> module = moduleRepository.findModuleByName(name);
        return module.orElse(null);
    }

    /**
     * Finds a lesson by its name.
     *
     * @param name the name of the lesson.
     * @return the lesson with the specified name, or null if not found.
     */
    public Lesson findLessonByName(String name){
        Optional<Lesson> module = lessonRepository.findLessonByName(name);
        return module.orElse(null);
    }

}