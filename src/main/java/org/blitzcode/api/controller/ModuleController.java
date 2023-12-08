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

@Service
public class ModuleController {
    @Autowired
    private ModuleRepository moduleRepository;
    @Autowired
    private LessonRepository lessonRepository;

    public List<Module> getAllModules() {
        return moduleRepository.findAll();
    }

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

    public List<Lesson> getLessonsFromModuleID(long moduleID){
        Optional<Module> moduleOptional = moduleRepository.findById(moduleID);
        if(moduleOptional.isEmpty()){
            throw new RuntimeException("");
        }

        return moduleOptional.get().getLessons();
    }

    public List<Question> getQuestionsFromLessonID(long lessonID){
        Optional<Lesson> lessonOptional = lessonRepository.findById(lessonID);
        if(lessonOptional.isEmpty()){
            throw new RuntimeException("Lesson not found with ID " + lessonID);
        }
        return lessonOptional.get().getQuestions();
    }

    public Module findModuleByName(String name){
        Optional<Module> module = moduleRepository.findModuleByName(name);
        return module.orElse(null);
    }

    public Lesson findLessonByName(String name){
        Optional<Lesson> module = lessonRepository.findLessonByName(name);
        return module.orElse(null);
    }

}