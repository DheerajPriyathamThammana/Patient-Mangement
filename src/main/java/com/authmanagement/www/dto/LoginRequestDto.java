package com.authmanagement.www.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequestDto {
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is Required")
    @Size(min = 6,max = 15)
    private String password;
}
