package org.blitzcode.api.controller;

import org.blitzcode.api.model.Lesson;
import org.blitzcode.api.model.Module;
import org.blitzcode.api.model.Question;
import org.blitzcode.api.repository.ModuleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ModuleController {
    private ModuleRepository moduleRepository;

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
}