package org.blitzcode.api.rest;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

// TODO: rate limit this endpoint
@RestController
public class PublicController {

    public record Language(String name, String imageFile) {}

    @GetMapping("/languages")
    public Language[] getSupportedBaseLanguages() {
        return new Language[]{
                new Language("Java", "java.png"),
                new Language("Python", "python.png"),
                new Language("JavaScript", "javascript.png")
        };
    }

    public record LoginInfo(String email, String password) {
        public Map<String, String> identityToolkitParams() {
            return Map.of("email", email, "password", password, "returnSecureToken", "true");
        }
    }

    @PostMapping("/signin")
    public String signIn(@RequestBody LoginInfo userInfo, HttpServletResponse response) throws IOException, InterruptedException {
        var params = userInfo.identityToolkitParams();
        var googleResponse = Firebase.send("identitytoolkit.googleapis.com/v1/accounts:signInWithPassword", params);
        return Firebase.passThrough(googleResponse, response);
    }

    @PostMapping("/signup")
    public String signUp(@RequestBody LoginInfo userInfo, HttpServletResponse response) throws IOException, InterruptedException {
        // TODO validation, error handling
        var params = userInfo.identityToolkitParams();
        var googleResponse = Firebase.send("identitytoolkit.googleapis.com/v1/accounts:signUp", params);
        return Firebase.passThrough(googleResponse, response);
    }

    // refresh token
    @PostMapping("/refresh-token")
    public String refreshToken(@RequestBody String refreshToken, HttpServletResponse response) throws IOException, InterruptedException {
        var params = Map.of("grant_type", "refresh_token", "refresh_token", refreshToken);
        var googleResponse = Firebase.send("securetoken.googleapis.com/v1/token", params);
        return Firebase.passThrough(googleResponse, response);
    }

    // send reset email
    @PostMapping("/send-reset-password-email")
    public String sendResetPasswordEmail(@RequestBody String email, HttpServletResponse response) throws IOException, InterruptedException {
        var params = Map.of("requestType", "PASSWORD_RESET", "email", email);
        var googleResponse = Firebase.send("identitytoolkit.googleapis.com/v1/accounts:sendOobCode", params);
        return Firebase.passThrough(googleResponse, response);
    }



}
