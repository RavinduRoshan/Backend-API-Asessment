package com.example.backendapiasessment.service.impl;

import com.example.backendapiasessment.dto.StudentDTO;
import com.example.backendapiasessment.entity.Student;
import com.example.backendapiasessment.exception.ApiException;
import com.example.backendapiasessment.mapper.MapStructMapper;
import com.example.backendapiasessment.repository.StudentRepository;
import com.example.backendapiasessment.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Autowired
    private MapStructMapper mapStructMapper;

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public void createStudent(StudentDTO studentDTO) {
        LOGGER.info("Ready to create a new student: [{}]", studentDTO);
        studentRepository.save(mapStructMapper.studentDTOToStudent(studentDTO));
        LOGGER.info("Successfully created the student. student: [{}]", studentDTO);
    }

    @Override
    public Student findByEmail(String email) {
        LOGGER.info("Preparing to find the student by email. email: [{}]", email);
        try {
            return studentRepository.findStudentByEmail(email);
        } catch (Exception e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
