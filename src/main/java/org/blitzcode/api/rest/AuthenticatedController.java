package org.blitzcode.api.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import org.blitzcode.api.controller.UserController;
import org.blitzcode.api.model.Language;
import org.blitzcode.api.rest.ResponseTypes.ModuleEntry;
import org.blitzcode.api.rest.ResponseTypes.Question;
import org.blitzcode.api.rest.ResponseTypes.ResetPasswordRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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

    @PutMapping(path = "/account/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody @Validated ResetPasswordRequest request,
                                                JwtAuthenticationToken token) throws IOException, InterruptedException {
        // TODO verify old password
        var params = Map.of("idToken", token.getToken().getTokenValue(), "password", request.newPassword(), "returnSecureToken", "true");
        var googleResponse = Firebase.send("identitytoolkit.googleapis.com/v1/accounts:update", params);
        return Firebase.passThrough(googleResponse);
    }

    @GetMapping(path = "/modules", produces = MediaType.APPLICATION_JSON_VALUE)
    public ModuleEntry[] getModules(JwtAuthenticationToken token) {
        return ModuleEntry.sample;
    }

    @GetMapping(path = "/questions/{lessonID}")
    public Question[] getQuestions(@PathVariable String lessonID, JwtAuthenticationToken token) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Adjust the file path as needed
            InputStream inputStream = getClass().getResourceAsStream("/JavaToPython/modules/Essentials/Printing.json");
            if (inputStream == null) {
                throw new FileNotFoundException("Resource not found: /JavaToPython/modules/Essentials/Printing.json");
            }
            List<QuestionItem> quizItems = mapper.readValue(inputStream, new TypeReference<List<QuestionItem>>() {});

            Question[] questions = new Question[quizItems.size()];
            // Now you can use the quizItems list
            for (int i = 0; i < questions.length; i++) {
                QuestionItem item = quizItems.get(i);
                int answerIndex = insertStringAtRandomIndex(item.wrongAnswers, item.answer);
                String[] arr = item.wrongAnswers.toArray(new String[0]);
                questions[i] = new Question(item.question, answerIndex, arr);
            }

            return questions;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Question[]{
                new Question("int x = 5;", 2, "const x = 5;", "int x = 5;", "let x = 5;"),
                new Question("String message = \"Hello\";", 1, "String message = \"Hello\";", "let message = \"Hello\";", "var message = \"Hello\";"),
                new Question("double price = 19.99;", 0, "let price = 19.99;", "const price = 19.99;", "double price = 19.99;"),
                new Question("boolean isValid = true;", 2, "const isValid = true;", "boolean isValid = true;", "let isValid = true;")
        };
    }

    public static int insertStringAtRandomIndex(List<String> stringList, String stringToInsert) {
        Random random = new Random();
        int insertIndex = random.nextInt(stringList.size() + 1);

        stringList.add(insertIndex, stringToInsert);
        return insertIndex;
    }

    @Getter
    @Setter
    private static class QuestionItem
    {
        private String question;
        private String answer;
        @JsonProperty("wrongAnswers")
        private List<String> wrongAnswers;

        // getters and setters
    }

    @PostMapping(path = "/questions/completed/{lessonID}")
    public Map<String, Integer> sectionCompleted(@PathVariable String lessonID, @RequestBody Question[] questions,
                                                 JwtAuthenticationToken token) {
        // TODO: save to database and fetch # of sections completed
        return Map.of("sectionsCompleted", (int) (Math.random() * 100), "sectionsTotal", 100);
    }

    @PostMapping(path = "/account/resetemail")
    public String resetEmail(@RequestBody @Email String newEmail, JwtAuthenticationToken token) {
        throw new UnsupportedOperationException();
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
