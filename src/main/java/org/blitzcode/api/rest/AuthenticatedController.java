package org.blitzcode.api.rest;

import lombok.Cleanup;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/auth")
public class AuthenticatedController {

    @GetMapping(path = "/test")
    public String test(Principal principal) {
        return principal.getName();
    }

    @GetMapping(path = "/modules")
    public String getModules() throws IOException {
        @Cleanup var is =  getClass().getResource("/placeholders/modules.json").openStream();
        return new String(is.readAllBytes());
    }

    @GetMapping(path = "/lessons")
    public String getLessons() throws IOException {
        @Cleanup var is =  getClass().getResource("/placeholders/lessons.json").openStream();
        return new String(is.readAllBytes());
    }

    public record ResetPasswordRequest(String oldPassword, String newPassword) {}

    @PostMapping(path = "/account/resetpassword")
    public String resetPassword(ResetPasswordRequest request) {
        throw new UnsupportedOperationException();
    }

    @PostMapping(path = "/account/resetemail")
    public String resetEmail(String newEmail) {
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
