package org.blitzcode.api.rest;

import org.springframework.boot.json.JsonParserFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;

@RestController
public class NoAuthController {

    private static final String FIREBASE_API_KEY = System.getenv("FIREBASE_API_KEY");

    public record Language(String name, String imageFile) {}

    @GetMapping("/languages")
    public Language[] getSupportedBaseLanguages() {
        return new Language[] {
                new Language("Java", "java.png"),
                new Language("Python", "python.png"),
                new Language("JavaScript", "javascript.png")
        };
    }

    public record UserInfo(String email, String password) {}

    // not sure if this is safe?
    @PostMapping("/signin")
    public String signIn(@RequestBody UserInfo userInfo) throws IOException, InterruptedException {
        try (var client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.NORMAL).build()) {
            var request = HttpRequest.newBuilder()
                    .uri(URI.create("https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=" + FIREBASE_API_KEY))
                    .POST(BodyPublishers.ofString("{\"email\": \"" + userInfo.email() + "\", \"password\": \"" + userInfo.password() + "\", \"returnSecureToken\": true}"))
                    .setHeader("content-type", "application/json")
                    .build();

            var response = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
            return (String) JsonParserFactory.getJsonParser().parseMap(response).get("idToken");
        }
    }

}
