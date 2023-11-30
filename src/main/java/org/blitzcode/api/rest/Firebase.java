package org.blitzcode.api.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Cleanup;
import lombok.experimental.UtilityClass;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@UtilityClass
public class Firebase {

    public final String FIREBASE_API_KEY = System.getenv("FIREBASE_API_KEY");
    private final ObjectMapper jsonMapper = Jackson2ObjectMapperBuilder.json().build();

    public String passThrough(HttpResponse<String> response, HttpServletResponse servletResponse) {
        servletResponse.setStatus(response.statusCode());
        servletResponse.setContentType(response.headers().map().get("content-type").getFirst());
        return response.body();
    }

    public HttpRequest request(String url, Object jsonBody) throws JsonProcessingException {
        return HttpRequest.newBuilder(URI.create("https://" + url + "?key=" +  Firebase.FIREBASE_API_KEY))
                .POST(HttpRequest.BodyPublishers.ofString(jsonMapper.writeValueAsString(jsonBody)))
                .setHeader("content-type", "application/json")
                .build();
    }

    public HttpResponse<String> send(String url, Object jsonBody) throws IOException, InterruptedException {
        @Cleanup var client = HttpClient.newHttpClient(); // TODO can this be shared globally?
        return client.send(request(url, jsonBody), HttpResponse.BodyHandlers.ofString());
    }

}