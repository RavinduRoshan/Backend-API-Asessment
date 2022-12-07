package com.example.backendapiasessment.web;

import com.example.backendapiasessment.dto.CommonStudentsDTO;
import com.example.backendapiasessment.dto.DeRegisterDTO;
import com.example.backendapiasessment.dto.Error;
import com.example.backendapiasessment.dto.RegisterDTO;
import com.example.backendapiasessment.exception.ApiException;
import com.example.backendapiasessment.service.RegistrationService;
import com.example.backendapiasessment.service.TeacherService;
import com.example.backendapiasessment.util.JsonConverter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(value = "Registration", description = "Rest API for student teacher registrations", tags = "Registration API")
public class RegistrationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private TeacherService teacherService;

    @ApiOperation(value = "Registers a student to a teacher", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = org.apache.http.HttpStatus.SC_CREATED, message = "CREATED"),
            @ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "Bad Request")
    })
    @PostMapping(value = "/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> registerStudent(@RequestBody RegisterDTO registerDTO) {
        LOGGER.info("Request received to register a student to a teacher. request: [{}]", registerDTO);
        try {
            registrationService.register(registerDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ApiException e) {
            Error error = new Error(e.getMessage(), e.getError().value());
            return new ResponseEntity<>(JsonConverter.toJson(error), e.getError());
        }
    }

    @ApiOperation(value = "Deregisters a student from a teacher", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = org.apache.http.HttpStatus.SC_CREATED, message = "CREATED"),
            @ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "Bad Request")
    })
    @PostMapping(value = "/deregister")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> deregisterStudent(@RequestBody DeRegisterDTO deRegisterDTO) {
        LOGGER.info("Request received to deregister a student from a teacher. request: [{}]", deRegisterDTO);
        try {
            registrationService.deregister(deRegisterDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ApiException e) {
            Error error = new Error(e.getMessage(), e.getError().value());
            return new ResponseEntity<>(JsonConverter.toJson(error), e.getError());
        }
    }

    @ApiOperation(value = "A list of students common to a given list of teachers", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = org.apache.http.HttpStatus.SC_CREATED, message = "CREATED"),
            @ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "Bad Request")
    })
    @GetMapping(value = "/commonstudents")
    @ResponseStatus(HttpStatus.CREATED)
    public CommonStudentsDTO getCommonStudents(@RequestParam("teacher") List<String> teachers) {
        LOGGER.info("Request received to get common students for teachers: [{}]", teachers);
        return registrationService.getCommonStudents(teachers);
    }
}
