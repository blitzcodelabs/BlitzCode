package org.blitzcode.api.rest;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Cleanup;
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

    public record ResetPasswordRequest(String oldPassword, @Size(min = 8) String newPassword) {}

    @PutMapping(path = "/account/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody @Validated ResetPasswordRequest request, JwtAuthenticationToken token) throws IOException, InterruptedException {
        // TODO verify old password
        var params = Map.of("idToken", token.getToken().getTokenValue(), "password", request.newPassword, "returnSecureToken", "true");
        var googleResponse = Firebase.send("identitytoolkit.googleapis.com/v1/accounts:update", params);
        return Firebase.passThrough(googleResponse);
    }

    @DeleteMapping(path = "/invalidate-token")
    public void invalidateTokens(JwtAuthenticationToken token) {
//        Firebase.auth().revokeRefreshTokens(token.getToken().getTokenValue());
        throw new UnsupportedOperationException();
    }

    @GetMapping(path = "/modules", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getModules() throws IOException {
        @Cleanup var is =  getClass().getResource("/placeholders/modules.json").openStream();
        return new String(is.readAllBytes());
    }

    @GetMapping(path = "/lessons", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getLessons() throws IOException {
        @Cleanup var is =  getClass().getResource("/placeholders/lessons.json").openStream();
        return new String(is.readAllBytes());
    }

    @PostMapping(path = "/account/resetemail")
    public String resetEmail(@Email String newEmail) {
        throw new UnsupportedOperationException();
    }

    @PostMapping(path = "/account/baseLanguage")
    public String setBaseLanguage(String newBaseLanguage) {
        throw new UnsupportedOperationException();
    }

    @GetMapping(path = "/account/baseLanguage")
    public String getBaseLanguage() {
        throw new UnsupportedOperationException();
    }

    @PostMapping(path = "/account/targetLanguage")
    public String setTargetLanguage(String newTargetLanguage) {
        throw new UnsupportedOperationException();
    }

    @GetMapping(path = "/account/targetLanguage")
    public String getTargetLanguage() {
        throw new UnsupportedOperationException();
    }

}
