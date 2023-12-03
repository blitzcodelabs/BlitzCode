package org.blitzcode.api.rest;

import jakarta.validation.constraints.Email;
import org.blitzcode.api.rest.ResponseTypes.*;
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

    @GetMapping(path = "/test")
    public Map<String, String> test(JwtAuthenticationToken token) {
        return Map.of("user id", token.getName(), "token", token.getToken().getTokenValue());
    }

    @DeleteMapping(path = "/account")
    public ResponseEntity<String> deleteAccount(JwtAuthenticationToken token) throws IOException, InterruptedException {
        // TODO should it verify password?
        var params = Map.of("idToken", token.getToken().getTokenValue());
        var googleResponse = Firebase.send("identitytoolkit.googleapis.com/v1/accounts:delete", params);
        return Firebase.passThrough(googleResponse);
    }

    @PutMapping(path = "/account/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody @Validated ResetPasswordRequest request, JwtAuthenticationToken token) throws IOException, InterruptedException {
        // TODO verify old password
        var params = Map.of("idToken", token.getToken().getTokenValue(), "password", request.newPassword(), "returnSecureToken", "true");
        var googleResponse = Firebase.send("identitytoolkit.googleapis.com/v1/accounts:update", params);
        return Firebase.passThrough(googleResponse);
    }

    @GetMapping(path = "/modules", produces = MediaType.APPLICATION_JSON_VALUE)
    public ModuleEntry[] getModules() {
        return ModuleEntry.sample;
    }

    @GetMapping(path = "/questions/{lessonID}")
    public Question[] getQuestions(@PathVariable String lessonID) {
        return new Question[]{
                new Question("int x = 5;", 2, "const x = 5;", "int x = 5;", "let x = 5;"),
                new Question("String message = \"Hello\";", 1, "String message = \"Hello\";", "let message = \"Hello\";", "var message = \"Hello\";"),
                new Question("double price = 19.99;", 0, "let price = 19.99;", "const price = 19.99;", "double price = 19.99;"),
                new Question("boolean isValid = true;", 2, "const isValid = true;", "boolean isValid = true;", "let isValid = true;")
        };
    }

    @PostMapping(path = "/questions/completed/{lessonID}")
    public void sectionCompleted(@PathVariable String lessonID, @RequestBody Question[] questions) {
        // TODO: save to database
    }

    @PostMapping(path = "/account/resetemail")
    public String resetEmail(@RequestBody @Email String newEmail) {
        throw new UnsupportedOperationException();
    }

    private String baseLanguage = "Java";

    @PostMapping(path = "/account/baseLanguage")
    public void setBaseLanguage(@RequestBody String baseLanguage) {
        this.baseLanguage = baseLanguage;
    }

    @GetMapping(path = "/account/baseLanguage")
    public String getBaseLanguage() {
        return baseLanguage;
    }

    private String targetLanguage = "Python";

    @PostMapping(path = "/account/targetLanguage")
    public void setTargetLanguage(@RequestBody String targetLanguage) {
        this.targetLanguage = targetLanguage;
    }

    @GetMapping(path = "/account/targetLanguage")
    public String getTargetLanguage() {
        return targetLanguage;
    }

}
