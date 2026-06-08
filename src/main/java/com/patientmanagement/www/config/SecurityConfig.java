package com.patientmanagement.www.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. Disable CSRF protection so Postman can make POST/PUT requests safely
                .csrf(csrf -> csrf.disable())

                // 2. Permit everyone to access the patient endpoints for now
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/patients/**").permitAll() // Allows /patients without any login
                        .anyRequest().authenticated()
                )

                // 3. Keep basic login capabilities active if needed later
                .httpBasic(withDefaults());

        return http.build();
    }
}