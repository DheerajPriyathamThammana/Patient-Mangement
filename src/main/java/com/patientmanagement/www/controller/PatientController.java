package com.patientmanagement.www.controller;

import com.patientmanagement.www.dto.PatientRequestDto;
import com.patientmanagement.www.dto.PatientResponseDto;
import com.patientmanagement.www.dto.validators.CreatePatientValidatorGroup;
import com.patientmanagement.www.model.Patient;
import com.patientmanagement.www.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.groups.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    PatientService patientService;

    @GetMapping
    @Operation(summary = "getPatients")
    public ResponseEntity<List<PatientResponseDto>> GetPatients(){
        List<PatientResponseDto> patients=patientService.getPatients();
        return ResponseEntity.ok().body(patients);
    }

    @PostMapping
    @Operation(summary = "Create a new patient")
    public ResponseEntity<PatientResponseDto> createPatient(
            @Validated({Default.class, CreatePatientValidatorGroup.class})
            @RequestBody PatientRequestDto patientRequestDto){
        PatientResponseDto patientResponseDto=patientService.createPatient(patientRequestDto);

        return ResponseEntity.ok().body(patientResponseDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Patient")
    public  ResponseEntity<PatientResponseDto> updatePatient(@PathVariable UUID id,
            @Validated({Default.class})
            @RequestBody PatientRequestDto patientRequestDto){
        PatientResponseDto patientResponseDto=patientService.updatePatient(id,patientRequestDto);

        return ResponseEntity.ok().body(patientResponseDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a patient")
    public ResponseEntity<PatientResponseDto> deletePatient(@PathVariable UUID id){
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
