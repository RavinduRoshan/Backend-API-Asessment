package com.example.backendapiasessment.service;

import com.example.backendapiasessment.dto.TeacherDTO;
import com.example.backendapiasessment.dto.TeachersDTO;
import com.example.backendapiasessment.entity.Teacher;

/**
 * Teacher Service Interface
 */
public interface TeacherService {

    /**
     * Get all teachers with the assigned students
     * @return TeachersDTO
     */
    TeachersDTO getAllTeachers();

    /**
     * Create a new teacher
     * @param teacher Teacher Object
     */
    void createTeacher(TeacherDTO teacher);

    /**
     * Find the teacher by email
     * @param email Teacher email
     * @return Teacher
     */
    Teacher findByEmail(String email);
}
