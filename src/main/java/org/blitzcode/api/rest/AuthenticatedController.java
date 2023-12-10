package org.blitzcode.api.rest;

import jakarta.validation.constraints.Email;
import org.blitzcode.api.controller.ModuleController;
import org.blitzcode.api.controller.UserController;
import org.blitzcode.api.model.Language;
import org.blitzcode.api.model.Lesson;
import org.blitzcode.api.model.Module;
import org.blitzcode.api.rest.ResponseTypes.LessonEntry;
import org.blitzcode.api.rest.ResponseTypes.ModuleEntry;
import org.blitzcode.api.rest.ResponseTypes.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/auth")
public class AuthenticatedController {


    @Autowired
    private UserController userController;

    @Autowired
    private ModuleController moduleController;

    @GetMapping(path = "/test")
    public Map<String, String> test(JwtAuthenticationToken token) {
        return Map.of("user id", getUserID(token), "token", token.getToken().getTokenValue());
    }

    @DeleteMapping(path = "/account")
    public ResponseEntity<String> deleteAccount(JwtAuthenticationToken token) throws IOException, InterruptedException {
        // TODO should it verify password?
        var params = Map.of("idToken", token.getToken().getTokenValue());
        var googleResponse = Firebase.send("identitytoolkit.googleapis.com/v1/accounts:delete", params);
        var user = userController.getUserByID(token);
        if (user != null) {
            userController.deleteUser(user);
        }
        return Firebase.passThrough(googleResponse);
    }

    @GetMapping(path = "/modules", produces = MediaType.APPLICATION_JSON_VALUE)
    public ModuleEntry[] getModules(JwtAuthenticationToken token) {
        List<Module> modules = moduleController.getAllModules();
        ModuleEntry[] moduleEntries = new ModuleEntry[modules.size()];
        for (int i = 0; i < modules.size(); i++) {
            Module currentModule = modules.get(i);
            LessonEntry[] lessonEntries = new LessonEntry[currentModule.getLessons().size()];
            for (int j = 0; j < lessonEntries.length; j++) {
                Lesson currentLesson = currentModule.getLessons().get(j);
                lessonEntries[j] = new LessonEntry(currentLesson.getName(), currentLesson.getId().toString(), 0, currentLesson.getPoints());
            }
            moduleEntries[i] = new ModuleEntry(currentModule.getName(), currentModule.getId().toString(), lessonEntries);
        }
        return moduleEntries;
    }

    @GetMapping(path = "/questions/{lessonID}")
    public Question[] getQuestions(@PathVariable long lessonID) {
        List<org.blitzcode.api.model.Question> questions = moduleController.getQuestionsFromLessonID(lessonID);
        Question[] formattedQuestions = new Question[questions.size()];
        for (int i = 0; i < questions.size(); i++) {
            org.blitzcode.api.model.Question question = questions.get(i);
            int answerIndex = insertStringAtRandomIndex(question.getWrongOptions(), question.getCorrectAnswer());
            String[] arr = question.getWrongOptions().toArray(new String[0]);
            formattedQuestions[i] = new Question(question.getText(), answerIndex, arr);
        }
        return formattedQuestions;
    }

    public static int insertStringAtRandomIndex(List<String> stringList, String stringToInsert) {
        Random random = new Random();
        int insertIndex = random.nextInt(stringList.size() + 1);

        stringList.add(insertIndex, stringToInsert);
        return insertIndex;
    }

    @PostMapping(path = "/questions/completed/{lessonID}")
    public Map<String, Integer> sectionCompleted(@PathVariable String lessonID, @RequestBody Question[] questions,
                                                 JwtAuthenticationToken token) {
        // TODO: save to database and fetch # of sections completed
        return Map.of("sectionsCompleted", 25, "sectionsTotal", 100);
    }

    @PostMapping(path = "/account/resetemail")
    public ResponseEntity<String> resetEmail(@RequestBody @Email String newEmail, JwtAuthenticationToken token) throws IOException, InterruptedException {
        var params = Map.of("idToken", token.getToken().getTokenValue(), "email", newEmail, "returnSecureToken", "false");
        var googleResponse = Firebase.send("identitytoolkit.googleapis.com/v1/accounts:update", params);
        if (HttpStatus.valueOf(googleResponse.statusCode()).is2xxSuccessful()) {
            Firebase.verifyEmail(token.getToken().getTokenValue());
        }
        return Firebase.passThrough(googleResponse);  // this returns passwordHash, is that ok?
    }

    @PostMapping(path = "/account/targetLanguage")
    public void setTargetLanguage(@RequestBody Language targetLanguage, JwtAuthenticationToken token) {
        userController.updateUserTargetLanguage(getUserID(token), targetLanguage);
    }

    @PostMapping(path = "/account/baseLanguage")
    public void setBaseLanguage(@RequestBody Language targetLanguage, JwtAuthenticationToken token) {
        userController.updateUserBaseLanguage(getUserID(token), targetLanguage);
    }

    @GetMapping(path = "/account/targetLanguage")
    public Language getTargetLanguage(JwtAuthenticationToken token) {
        return userController.getUserByID(token).getTargetLanguage();
    }

    @GetMapping(path = "/account/baseLanguage")
    public Language getBaseLanguage(JwtAuthenticationToken token) {
        return userController.getUserByID(token).getBaseLanguage();
    }

    private static String getUserID(JwtAuthenticationToken token) {
        return token.getName();
    }

}
