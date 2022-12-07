package com.example.backendapiasessment.web;

import com.example.backendapiasessment.dto.StudentDTO;
import com.example.backendapiasessment.entity.Student;
import com.example.backendapiasessment.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/students"})
@Api(value = "Student", description = "Rest API for student related operations", tags = "Student API")
public class StudentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);
    @Autowired
    private StudentService studentService;


    @ApiOperation(value = "Creates a new student", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = org.apache.http.HttpStatus.SC_CREATED, message = "CREATED"),
            @ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "Bad Request")
    })
    @PostMapping(value = "")
    @ResponseStatus(HttpStatus.CREATED)
    public void createStudent(@RequestBody StudentDTO studentDTO) {
        LOGGER.info("Request received to create a student. student: [{}]", studentDTO);
        studentService.createStudent(studentDTO);
    }

    @ApiOperation(value = "Get all students", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = org.apache.http.HttpStatus.SC_OK, message = "OK"),
            @ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "Bad Request")
    })
    @GetMapping(value = "")
    @ResponseStatus(HttpStatus.OK)
    public List<Student> gwtAllStudents() {
        LOGGER.info("Request received to get all students.");
        return studentService.getAllStudents();
    }
}
