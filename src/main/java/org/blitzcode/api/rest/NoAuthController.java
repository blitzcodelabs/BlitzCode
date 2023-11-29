package org.blitzcode.api.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoAuthController {

    @GetMapping("/languages")
    public String[] getLanguages() {
        return new String[] { "Java", "Python", "C++", "C", "C#", "JavaScript"};
    }
}
