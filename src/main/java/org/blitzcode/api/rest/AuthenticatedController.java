package org.blitzcode.api.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
public class AuthenticatedController {

    @GetMapping(path = "/test")
    public String test(Principal principal) {
        return principal.getName();
    }

}
