package com.patientmanagement.www.dto;


import com.patientmanagement.www.dto.validators.CreatePatientValidatorGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PatientRequestDto {

    @NotBlank(message = "Name is required")
    @Size(max=100 ,message = "Name cannot exceed 100 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Size(message = "Enter a valid Email")
    private String email;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Date of Birth is required")
    private String dateofbirth;

    @NotBlank(groups = CreatePatientValidatorGroup.class,message = "Registered date is required")
    private String registereddate;

}
