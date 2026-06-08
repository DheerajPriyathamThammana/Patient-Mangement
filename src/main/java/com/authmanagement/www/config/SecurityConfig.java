package com.authmanagement.www.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor // Automatically injects the JwtAuthFilter constructor
public class SecurityConfig {

    // Inject your custom JWT Filter that we saw in your very first error log
    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. Disable CSRF because we are using JWT tokens, not session cookies
                .csrf(csrf -> csrf.disable())

                // 2. Protect your endpoints correctly for an Auth Service
                .authorizeHttpRequests(auth -> auth
                        // Allow ANYONE to hit login, register, and documentation (Swagger)
                        .requestMatchers("/auth/login", "/auth/register", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
                        // Everything else (like /auth/validate or admin tools) requires authentication
                        .anyRequest().authenticated()
                )

                // 3. Make the session STATELESS (Crucial for JWT architecture)
                // This tells Spring not to create or store HTTP sessions on the server
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // 4. Add your JWT Filter right before Spring's default username/password filter
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}