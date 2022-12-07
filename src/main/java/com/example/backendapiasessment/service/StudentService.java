package com.example.backendapiasessment.service;

import com.example.backendapiasessment.dto.StudentDTO;
import com.example.backendapiasessment.entity.Student;

import java.util.List;

/**
 * Student Service Interface
 */
public interface StudentService {

    /**
     * Get all saved students
     * @return List of Student
     */
    List<Student> getAllStudents();

    /**
     * Create a new student
     * @param studentDTO Student details
     */
    void createStudent(StudentDTO studentDTO);

    /**
     * Find a student by email
     * @param email student email
     * @return Student
     */
    Student findByEmail(String email);
}
