package com.authmanagement.www.service;

import com.authmanagement.www.dto.LoginRequestDto;
import com.authmanagement.www.dto.RegisterRequestDto;
import com.authmanagement.www.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public Optional<String> authenticate(LoginRequestDto loginRequestDto){

        Optional<String> token=userService.findByEmail(loginRequestDto.getEmail())
                .filter(u->passwordEncoder.matches(loginRequestDto.getPassword(), u.getPassword()))
                .map(u->jwtUtil.generateToken(u.getEmail(), u.getRole()));

        return token;
    }

    public boolean validateToken(String token){
        try{
            jwtUtil.validateToken(token);
            return true;
        }catch (Exception ex){
            return false;
        }
    }

    public boolean registerUser(RegisterRequestDto registerRequestDto) {
        // 1. Check if a user with this email already exists via your UserService
        if (userService.existsByEmail(registerRequestDto.getEmail())) {
            return false;
        }

        // 2. Hash the raw password safely
        String hashedPassword = passwordEncoder.encode(registerRequestDto.getPassword());

        // 3. Update the DTO or object with the hashed password before saving
        registerRequestDto.setPassword(hashedPassword);

        // 4. Delegate the actual database save operation to your UserService
        userService.saveUser(registerRequestDto);
        return true;
    }
}
