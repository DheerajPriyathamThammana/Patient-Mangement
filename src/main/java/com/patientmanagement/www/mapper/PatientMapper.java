package com.patientmanagement.www.mapper;

import com.patientmanagement.www.dto.PatientRequestDto;
import com.patientmanagement.www.dto.PatientResponseDto;
import com.patientmanagement.www.model.Patient;

import java.util.UUID;

public class PatientMapper {
    public static PatientResponseDto toDto(Patient patient){
        PatientResponseDto patientresponsedto=new PatientResponseDto();
        patientresponsedto.setId(patient.getId().toString());
        patientresponsedto.setName(patient.getName());
        patientresponsedto.setEmail(patient.getEmail());
        patientresponsedto.setAddress(patient.getAddress());
        patientresponsedto.setDateofbirth(patient.getDateofbirth());

        return patientresponsedto;
    }
    public static Patient toModel(PatientRequestDto patientRequestDto){
        Patient patient=new Patient();
        patient.setName(patientRequestDto.getName());
        patient.setEmail(patientRequestDto.getEmail());
        patient.setAddress(patientRequestDto.getAddress());
        patient.setDateofbirth(patientRequestDto.getDateofbirth());

        return patient;
    }
}
