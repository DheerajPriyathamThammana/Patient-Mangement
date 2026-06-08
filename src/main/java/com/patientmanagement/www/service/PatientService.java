package com.patientmanagement.www.service;

import com.patientmanagement.www.dto.PatientRequestDto;
import com.patientmanagement.www.dto.PatientResponseDto;
import com.patientmanagement.www.exception.EmailAlreadyExistException;
import com.patientmanagement.www.exception.PatientNotFoundException;
import com.patientmanagement.www.mapper.PatientMapper;
import com.patientmanagement.www.model.Patient;
import com.patientmanagement.www.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PatientService {
    @Autowired
    PatientRepository patientRepository;

    public List<PatientResponseDto> getPatients(){
        List<Patient> patients= patientRepository.findAll();
        return patients.stream().map(PatientMapper::toDto).toList();
    }

    public PatientResponseDto createPatient(PatientRequestDto patientRequestDto) {
        if(patientRepository.existsByEmail(patientRequestDto.getEmail())){
            throw new EmailAlreadyExistException("A Patient with this Email"+patientRequestDto.getEmail()+" already Exist");
        }
        Patient newpatient=patientRepository.save(PatientMapper.toModel(patientRequestDto));
        return PatientMapper.toDto(newpatient);
    }

    public PatientResponseDto updatePatient(UUID id,PatientRequestDto patientRequestDto){
        Patient patient=patientRepository.findById(id).orElseThrow(()->new PatientNotFoundException("Patient Not Found With Id "+id));

        patient.setName(patientRequestDto.getName());
        patient.setEmail(patientRequestDto.getEmail());
        patient.setAddress(patientRequestDto.getAddress());
        patient.setDateofbirth(patientRequestDto.getDateofbirth());

        Patient updatedpatient=patientRepository.save(patient);
        return PatientMapper.toDto(updatedpatient);
    }

    public void deletePatient(UUID id){
        patientRepository.deleteById(id);
    }
}
