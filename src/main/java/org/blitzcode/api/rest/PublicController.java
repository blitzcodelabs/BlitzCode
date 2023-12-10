package org.blitzcode.api.rest;

import jakarta.validation.constraints.Email;
import org.blitzcode.api.controller.UserController;
import org.blitzcode.api.model.Language;
import org.blitzcode.api.model.User;
import org.blitzcode.api.rest.ResponseTypes.LoginInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

/**
 * This class handles all requests not requiring authentication.
 */
@RestController
@RequestMapping
public class PublicController {

    @Autowired private UserController userController;

    @GetMapping("/languages")
    public Language[] getSupportedBaseLanguages() {
        return Language.values();
    }

    /**
     * Sign in with email/password.
     * <a href="https://firebase.google.com/docs/reference/rest/auth#section-sign-in-email-password">Docs</a>
     */
    @PostMapping("/signin")
    public ResponseEntity<String> signIn(@RequestBody @Validated LoginInfo userInfo) throws IOException, InterruptedException {
        var params = userInfo.identityToolkitParams();
        var googleResponse = Firebase.send("identitytoolkit.googleapis.com/v1/accounts:signInWithPassword", params);
        if (HttpStatusCode.valueOf(googleResponse.statusCode()).is2xxSuccessful()
                && userController.getUserByID(Firebase.getUserID(googleResponse)) == null) {
            var user = new User();
            user.setId(Firebase.getUserID(googleResponse));
            user.setEmail(userInfo.email());
            userController.createUser(user);
        }
        return Firebase.passThrough(googleResponse);
    }

    /**
     * Sign up with email/password, language preferences, then send verification email.
     * <a href="https://firebase.google.com/docs/reference/rest/auth#section-create-email-password">Docs</a>
     */
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody @Validated LoginInfo userInfo,
                                         @RequestParam Language baseLanguage,
                                         @RequestParam Language targetLanguage) throws IOException, InterruptedException {
        // TODO validation, error handling
        var params = userInfo.identityToolkitParams();
        var googleResponse = Firebase.send("identitytoolkit.googleapis.com/v1/accounts:signUp", params);
        if (HttpStatus.valueOf(googleResponse.statusCode()).is2xxSuccessful()) {
            Firebase.verifyEmail(Firebase.getJwt(googleResponse));
        }
        var user = new User(Firebase.getUserID(googleResponse), userInfo.email(), baseLanguage, targetLanguage, null);
        userController.createUser(user);
        return Firebase.passThrough(googleResponse);
    }

    /**
     * Refresh the user's JWT token.
     * <a href="https://firebase.google.com/docs/reference/rest/auth#section-refresh-token">Docs</a>
     */
    // refresh token
    @PostMapping("/refresh-token")
    public ResponseEntity<String> refreshToken(@RequestBody String refreshToken) throws IOException, InterruptedException {
        var params = Map.of("grant_type", "refresh_token", "refresh_token", refreshToken);
        var googleResponse = Firebase.send("securetoken.googleapis.com/v1/token", params);
        return Firebase.passThrough(googleResponse);
    }

    /**
     * Send a password reset email.
     * <a href="https://firebase.google.com/docs/reference/rest/auth#section-send-password-reset-email">Docs</a>
     */
    // send reset email
    @PostMapping("/send-reset-password-email")
    public ResponseEntity<String> sendResetPasswordEmail(@RequestBody @Email String email) throws IOException, InterruptedException {
        var params = Map.of("requestType", "PASSWORD_RESET", "email", email);
        var googleResponse = Firebase.send("identitytoolkit.googleapis.com/v1/accounts:sendOobCode", params);
        return Firebase.passThrough(googleResponse);
    }

}
