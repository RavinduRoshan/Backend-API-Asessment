package com.example.backendapiasessment.web;

import com.example.backendapiasessment.dto.TeacherDTO;
import com.example.backendapiasessment.dto.TeachersDTO;
import com.example.backendapiasessment.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/teachers"})
@Api(value = "Teacher", description = "Rest API for teacher related operations", tags = "Teacher API")
public class TeacherController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeacherController.class);

    @Autowired
    private TeacherService teacherService;

    @ApiOperation(value = "Creates a new teacher", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = org.apache.http.HttpStatus.SC_CREATED, message = "CREATED"),
            @ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "Bad Request")
    })
    @PostMapping(value = "")
    @ResponseStatus(HttpStatus.CREATED)
    public void createTeacher(@RequestBody TeacherDTO teacher) {
        LOGGER.info("Request received to create a teacher. teacher: [{}]", teacher);
        teacherService.createTeacher(teacher);
    }

    @ApiOperation(value = "Get all teachers", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = org.apache.http.HttpStatus.SC_OK, message = "OK"),
            @ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "Bad Request")
    })
    @GetMapping(value = "")
    @ResponseStatus(HttpStatus.OK)
    public TeachersDTO getAllTeachers() {
        LOGGER.info("Request received to get all teachers along with the student list.");
        return teacherService.getAllTeachers();
    }
}
