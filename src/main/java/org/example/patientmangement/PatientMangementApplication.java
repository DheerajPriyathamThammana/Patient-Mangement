package org.example.patientmangement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"org.example.patientmangement", "com.authmanagement.www", "com.patientmanagement.www"})
public class PatientMangementApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatientMangementApplication.class, args);
    }

}
