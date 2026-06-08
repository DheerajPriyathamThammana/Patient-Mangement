package com.authmanagement.www.controller;


import com.authmanagement.www.dto.LoginRequestDto;
import com.authmanagement.www.dto.LoginResponseDto;
import com.authmanagement.www.dto.RegisterRequestDto;
import com.authmanagement.www.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @Operation(summary = "Login an User")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto){
        Optional<String> tokenOptional=authService.authenticate(loginRequestDto);

        if(tokenOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token=tokenOptional.get();
        return ResponseEntity.ok(new LoginResponseDto(token));
    }

    @Operation(summary = "Validate the token")
    @GetMapping("/validate")
    public ResponseEntity<Void> validateToken(@RequestHeader("Authorization") String authHeader){

        if(authHeader==null || !authHeader.startsWith("Bearer ")){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return authService.validateToken(authHeader.substring(7))?
                ResponseEntity.ok().build()
               : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    }

    @Operation(summary = "Register a new User")
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDto registerRequestDto) {
        boolean isRegistered = authService.registerUser(registerRequestDto);

        if (!isRegistered) {
            // Returns 400 Bad Request if username/email already exists
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username or Email already taken.");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully!");
    }
}
