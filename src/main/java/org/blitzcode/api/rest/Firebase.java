package org.blitzcode.api.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Cleanup;
import lombok.experimental.UtilityClass;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

@UtilityClass
public class Firebase {

    public final String FIREBASE_API_KEY = System.getenv("FIREBASE_API_KEY");
    private final ObjectMapper jsonMapper = Jackson2ObjectMapperBuilder.json().build();

    public ResponseEntity<String> passThrough(HttpResponse<String> response) {
        return ResponseEntity.status(response.statusCode())
                .contentType(MediaType.valueOf(response.headers().map().get("content-type").getFirst()))
                .body(response.body());
    }

    public HttpRequest request(String url, Object jsonBody) throws JsonProcessingException {
        return HttpRequest.newBuilder(URI.create("https://" + url + "?key=" +  Firebase.FIREBASE_API_KEY))
                .POST(HttpRequest.BodyPublishers.ofString(jsonMapper.writeValueAsString(jsonBody)))
                .setHeader("content-type", "application/json")
                .build();
    }

    public String getUserID(HttpResponse<String> response) throws IOException {
        return jsonMapper.readTree(response.body()).get("localId").asText();
    }

    public String getJwt(HttpResponse<String> response) throws IOException {
        return jsonMapper.readTree(response.body()).get("idToken").asText();
    }

    public HttpResponse<String> send(String url, Object jsonBody) throws IOException, InterruptedException {
        @Cleanup var client = HttpClient.newHttpClient(); // TODO can this be shared globally?
        return client.send(request(url, jsonBody), HttpResponse.BodyHandlers.ofString());
    }

    public HttpResponse<String> verifyEmail(String token) throws IOException, InterruptedException {
        var params = Map.of("idToken", token, "requestType", "VERIFY_EMAIL");
        return Firebase.send("identitytoolkit.googleapis.com/v1/accounts:sendOobCode", params);
    }

}
