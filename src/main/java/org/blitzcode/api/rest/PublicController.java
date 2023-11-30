package org.blitzcode.api.rest;

import lombok.Cleanup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
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

// TODO: rate limit this endpoint
@RestController
public class PublicController {

    private static final String FIREBASE_API_KEY = System.getenv("FIREBASE_API_KEY");

    @Autowired
    private MappingJackson2HttpMessageConverter springMvcJacksonConverter;

    public record Language(String name, String imageFile) {}

    @GetMapping("/languages")
    public Language[] getSupportedBaseLanguages() {
        return new Language[]{
                new Language("Java", "java.png"),
                new Language("Python", "python.png"),
                new Language("JavaScript", "javascript.png")
        };
    }

    public record LoginInfo(String email, String password) {}

    public record IdentityToolKitRequest(String email, String password, boolean returnSecureToken) {}

    @PostMapping("/signin")
    public String signIn(@RequestBody LoginInfo userInfo) throws IOException, InterruptedException {
        @Cleanup var client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.NORMAL).build();
        var req = new IdentityToolKitRequest(userInfo.email(), userInfo.password(), true);
        var request = HttpRequest.newBuilder()
                .uri(URI.create("https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=" + FIREBASE_API_KEY))
                .POST(BodyPublishers.ofString(springMvcJacksonConverter.getObjectMapper().writeValueAsString(req)))
                .setHeader("content-type", "application/json")
                .build();

        var response = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        return (String) JsonParserFactory.getJsonParser().parseMap(response).get("idToken");
    }

    @PostMapping("/signup")
    public String signUp(@RequestBody LoginInfo userInfo) throws IOException, InterruptedException {
        // TODO validation, error handling
        @Cleanup var client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.NORMAL).build();
        var req = new IdentityToolKitRequest(userInfo.email(), userInfo.password(), true);
        var request = HttpRequest.newBuilder()
                .uri(URI.create("https://identitytoolkit.googleapis.com/v1/accounts:signUp?key=" + FIREBASE_API_KEY))
                .POST(BodyPublishers.ofString(springMvcJacksonConverter.getObjectMapper().writeValueAsString(req)))
                .setHeader("content-type", "application/json")
                .build();

        var response = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        return (String) JsonParserFactory.getJsonParser().parseMap(response).get("idToken");
    }

}
