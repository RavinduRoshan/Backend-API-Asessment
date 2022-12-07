package com.example.backendapiasessment.service;

import com.example.backendapiasessment.dto.CommonStudentsDTO;
import com.example.backendapiasessment.dto.DeRegisterDTO;
import com.example.backendapiasessment.dto.RegisterDTO;
import com.example.backendapiasessment.exception.ApiException;

import java.util.List;

/**
 * Teacher-Student Registration Service Interface
 */
public interface RegistrationService {
    /**
     * Register a student to a teacher
     * @param registerDTO registration details
     * @throws ApiException
     */
    void register(RegisterDTO registerDTO) throws ApiException;

    /**
     * De-register a student from a teacher
     * @param deRegisterDTO De registration details
     * @throws ApiException
     */
    void deregister(DeRegisterDTO deRegisterDTO) throws  ApiException;

    /**
     * Get common student list for a given list of teachers
     * @param teachers teachers email list
     * @return CommonStudentDTO
     * @throws ApiException
     */
    CommonStudentsDTO getCommonStudents(List<String> teachers) throws ApiException;
}
