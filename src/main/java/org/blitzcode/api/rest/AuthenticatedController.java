package org.blitzcode.api.rest;

import jakarta.validation.constraints.Email;
import org.blitzcode.api.controller.UserController;
import org.blitzcode.api.model.Language;
import org.blitzcode.api.rest.ResponseTypes.ModuleEntry;
import org.blitzcode.api.rest.ResponseTypes.Question;
import org.blitzcode.api.rest.ResponseTypes.ResetPasswordRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

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
        return new Question[]{
                new Question("int x = 5;", 2, "const x = 5;", "int x = 5;", "let x = 5;"),
                new Question("String message = \"Hello\";", 1, "String message = \"Hello\";", "let message = \"Hello\";", "var message = \"Hello\";"),
                new Question("double price = 19.99;", 0, "let price = 19.99;", "const price = 19.99;", "double price = 19.99;"),
                new Question("boolean isValid = true;", 2, "const isValid = true;", "boolean isValid = true;", "let isValid = true;")
        };
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
            return ResponseEntity.status(HttpStatus.OK).body(lang.name());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Target Language did not match a valid language " + targetLanguage);
        }
    }

    @PostMapping(path = "/account/baseLanguage")
    public ResponseEntity<String> setBaseLanguage(@RequestBody String targetLanguage, JwtAuthenticationToken token) {
        try {
            Language lang = Language.valueOf(targetLanguage.toUpperCase());
            userController.updateUserBaseLanguage(getUserID(token), Language.valueOf(targetLanguage.toUpperCase()));
            return ResponseEntity.status(HttpStatus.OK).body(lang.name());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Target Language did not match a valid language " + targetLanguage);
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
