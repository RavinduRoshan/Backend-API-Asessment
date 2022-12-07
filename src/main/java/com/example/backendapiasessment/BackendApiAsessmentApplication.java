package com.example.backendapiasessment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages={
        "com.example.backendapiasessment", "com.example.backendapiasessment"})
@EnableJpaRepositories(basePackages="com.example.backendapiasessment.repository")
@EntityScan(basePackages="com.example.backendapiasessment.entity")
public class BackendApiAsessmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApiAsessmentApplication.class, args);
    }

}
