package org.example.teskmanagementbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan
public class TeskManagementBackendApplication {

    public static void main(String[] args) {

        SpringApplication.run(TeskManagementBackendApplication.class, args);
    }

}
