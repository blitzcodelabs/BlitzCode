package org.blitzcode.api.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.Email;
import org.blitzcode.api.controller.UserController;
import org.blitzcode.api.model.Language;
import org.blitzcode.api.rest.ResponseTypes.ModuleEntry;
import org.blitzcode.api.rest.ResponseTypes.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/auth")
public class AuthenticatedController {

    @Autowired
    private UserController userController;

    @GetMapping(path = "/test")
    public Map<String, String> test(JwtAuthenticationToken token) {
        return Map.of("user id", getUserID(token), "token", token.getToken().getTokenValue());
    }

    @DeleteMapping(path = "/account")
    public ResponseEntity<String> deleteAccount(JwtAuthenticationToken token) throws IOException, InterruptedException {
        // TODO should it verify password?
        var params = Map.of("idToken", token.getToken().getTokenValue());
        var googleResponse = Firebase.send("identitytoolkit.googleapis.com/v1/accounts:delete", params);
        userController.deleteUser(userController.getUserByID(token));
        return Firebase.passThrough(googleResponse);
    }

    @GetMapping(path = "/modules", produces = MediaType.APPLICATION_JSON_VALUE)
    public ModuleEntry[] getModules(JwtAuthenticationToken token) {
        return ModuleEntry.sample;
    }

    @GetMapping(path = "/questions/{lessonID}")
    public Question[] getQuestions(@PathVariable String lessonID, JwtAuthenticationToken token) throws IOException {
        var mapper = new ObjectMapper();
        // Adjust the file path as needed
        var inputStream = getClass().getResourceAsStream("/JavaToPython/modules/Essentials/Printing.json");
        if (inputStream == null) {
            throw new FileNotFoundException("Resource not found: /JavaToPython/modules/Essentials/Printing.json");
        }
        List<QuestionItem> quizItems = mapper.readValue(inputStream, new TypeReference<>() {});

        var questions = new Question[quizItems.size()];
        // Now you can use the quizItems list
        for (int i = 0; i < questions.length; i++) {
            QuestionItem item = quizItems.get(i);
            int answerIndex = insertStringAtRandomIndex(item.wrongAnswers, item.answer);
            String[] arr = item.wrongAnswers.toArray(new String[0]);
            questions[i] = new Question(item.question, answerIndex, arr);
        }

        return questions;
    }

    public static int insertStringAtRandomIndex(List<String> stringList, String stringToInsert) {
        Random random = new Random();
        int insertIndex = random.nextInt(stringList.size() + 1);

        stringList.add(insertIndex, stringToInsert);
        return insertIndex;
    }

    private record QuestionItem(String question, String answer, List<String> wrongAnswers) {}

    @PostMapping(path = "/questions/completed/{lessonID}")
    public Map<String, Integer> sectionCompleted(@PathVariable String lessonID, @RequestBody Question[] questions,
                                                 JwtAuthenticationToken token) {
        // TODO: save to database and fetch # of sections completed
        return Map.of("sectionsCompleted", (int) (Math.random() * 100), "sectionsTotal", 100);
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
    public ResponseEntity<String> setTargetLanguage(@RequestBody String targetLanguage, JwtAuthenticationToken token) {
        try {
            Language lang = Language.valueOf(targetLanguage.toUpperCase());
            userController.updateUserTargetLanguage(getUserID(token), Language.valueOf(targetLanguage.toUpperCase()));
            return ResponseEntity.ok(lang.name());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Target Language did not match a valid language " + targetLanguage);
        }
    }

    @PostMapping(path = "/account/baseLanguage")
    public ResponseEntity<String> setBaseLanguage(@RequestBody String targetLanguage, JwtAuthenticationToken token) {
        try {
            Language lang = Language.valueOf(targetLanguage.toUpperCase());
            userController.updateUserBaseLanguage(getUserID(token), Language.valueOf(targetLanguage.toUpperCase()));
            return ResponseEntity.ok(lang.name());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Target Language did not match a valid language " + targetLanguage);
        }
    }

    @GetMapping(path = "/account/targetLanguage")
    public String getTargetLanguage(JwtAuthenticationToken token) {
        return userController.getUserByID(token).getTargetLanguage().toString();
    }

    @GetMapping(path = "/account/baseLanguage")
    public String getBaseLanguage(JwtAuthenticationToken token) {
        return userController.getUserByID(token).getBaseLanguage().toString();
    }

    private static String getUserID(JwtAuthenticationToken token) {
        return token.getName();
    }

}
