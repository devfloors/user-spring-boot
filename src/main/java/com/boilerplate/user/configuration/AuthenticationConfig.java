package com.boilerplate.user.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AuthenticationConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests(
                        (requests) -> {
                            try {
                                requests
                                    .requestMatchers("/api/*/users/join","/api/*/users/login").permitAll()
                                    .requestMatchers("/api/**").authenticated()
                                    .and()
                                    .sessionManagement()
                                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                );

        return http.build();
    }
}
