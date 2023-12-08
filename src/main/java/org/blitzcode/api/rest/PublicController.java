package org.blitzcode.api.rest;

import jakarta.validation.constraints.Email;
import org.blitzcode.api.controller.ModuleController;
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

// TODO: rate limit this endpoint
@RestController
@RequestMapping
public class PublicController {

    @Autowired
    private UserController userController;
    @Autowired
    private ModuleController moduleController;

    @GetMapping("/languages")
    public Language[] getSupportedBaseLanguages() {
        return Language.values();
    }

    @PostMapping("/signin")
    public ResponseEntity<String> signIn(@RequestBody @Validated LoginInfo userInfo) throws IOException, InterruptedException {
        var params = userInfo.identityToolkitParams();
        var googleResponse = Firebase.send("identitytoolkit.googleapis.com/v1/accounts:signInWithPassword", params);
        if (HttpStatusCode.valueOf(googleResponse.statusCode()).is2xxSuccessful()
                && userController.getUserByID(Firebase.getUserID(googleResponse)) == null) {
            var user = new User();
            user.setId(Firebase.getUserID(googleResponse));
            user.setEmail(userInfo.email());
            user.setBaseLanguage(Language.JAVA);
            userController.createUser(user);
        }
        return Firebase.passThrough(googleResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody @Validated LoginInfo userInfo) throws IOException, InterruptedException {
        // TODO validation, error handling
        var params = userInfo.identityToolkitParams();
        var googleResponse = Firebase.send("identitytoolkit.googleapis.com/v1/accounts:signUp", params);
        if (HttpStatus.valueOf(googleResponse.statusCode()).is2xxSuccessful()) {
            Firebase.verifyEmail(Firebase.getJwt(googleResponse));
        }
        var user = new User();
        user.setId(Firebase.getUserID(googleResponse));
        user.setEmail(userInfo.email());
        userController.createUser(user);
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

    /*@PutMapping("/createModule")
    public ResponseEntity<String> createModule(@RequestBody Module module) {
        try {
            moduleController.createModule(module);
            return ResponseEntity.ok(module.toString());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }*/

}
