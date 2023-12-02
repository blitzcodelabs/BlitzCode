package org.blitzcode.api.rest;

import jakarta.validation.constraints.Email;
import org.blitzcode.api.rest.ResponseTypes.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

// TODO: rate limit this endpoint
@RestController
@RequestMapping
public class PublicController {

    @GetMapping("/languages")
    public Language[] getSupportedBaseLanguages() {
        return new Language[]{
                new Language("Java", "java.png"),
                new Language("Python", "python.png"),
                new Language("JavaScript", "javascript.png")
        };
    }

    @PostMapping("/signin")
    public ResponseEntity<String> signIn(@RequestBody @Validated LoginInfo userInfo) throws IOException, InterruptedException {
        var params = userInfo.identityToolkitParams();
        var googleResponse = Firebase.send("identitytoolkit.googleapis.com/v1/accounts:signInWithPassword", params);
        return Firebase.passThrough(googleResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody @Validated LoginInfo userInfo) throws IOException, InterruptedException {
        // TODO validation, error handling
        var params = userInfo.identityToolkitParams();
        var googleResponse = Firebase.send("identitytoolkit.googleapis.com/v1/accounts:signUp", params);
        return Firebase.passThrough(googleResponse);
    }

    // refresh token
    @PostMapping("/refresh-token")
    public ResponseEntity<String> refreshToken(@RequestBody String refreshToken) throws IOException, InterruptedException {
        var params = Map.of("grant_type", "refresh_token", "refresh_token", refreshToken);
        var googleResponse = Firebase.send("securetoken.googleapis.com/v1/token", params);
        return Firebase.passThrough(googleResponse);
    }

    // send reset email
    @PostMapping("/send-reset-password-email")
    public ResponseEntity<String> sendResetPasswordEmail(@RequestBody @Email String email) throws IOException, InterruptedException {
        var params = Map.of("requestType", "PASSWORD_RESET", "email", email);
        var googleResponse = Firebase.send("identitytoolkit.googleapis.com/v1/accounts:sendOobCode", params);
        return Firebase.passThrough(googleResponse);
    }



}
