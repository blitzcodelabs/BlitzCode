package org.blitzcode.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(a ->
                        a.requestMatchers("/auth/**").authenticated().anyRequest().anonymous())
                .oauth2ResourceServer(o ->
                        o.jwt(Customizer.withDefaults())) // TODO: look into using firebase admin sdk
                .build();
    }
}