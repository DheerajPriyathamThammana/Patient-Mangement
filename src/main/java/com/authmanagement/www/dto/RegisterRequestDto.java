package com.authmanagement.www.dto;

import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@Data
public class RegisterRequestDto {
    private String username;
    private String password;
    private String email;
}
