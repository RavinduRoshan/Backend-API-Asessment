package com.example.backendapiasessment.service.impl;

import com.example.backendapiasessment.dto.TeacherDTO;
import com.example.backendapiasessment.dto.TeacherInfoDTO;
import com.example.backendapiasessment.dto.TeachersDTO;
import com.example.backendapiasessment.entity.StudentRegistration;
import com.example.backendapiasessment.entity.Teacher;
import com.example.backendapiasessment.exception.ApiException;
import com.example.backendapiasessment.mapper.MapStructMapper;
import com.example.backendapiasessment.repository.StudentRegistrationRepository;
import com.example.backendapiasessment.repository.TeacherRepository;
import com.example.backendapiasessment.service.TeacherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeacherServiceImpl.class);

    @Autowired
    private MapStructMapper mapStructMapper;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private StudentRegistrationRepository studentRegistrationRepository;

    @Override
    public TeachersDTO getAllTeachers() {
        LOGGER.info("Preparing to get all teachers..");
        TeachersDTO teachers = new TeachersDTO();
        List<TeacherInfoDTO> teacherInfoDTOList = new ArrayList<>();

        List<Teacher> teacherList = teacherRepository.findAll();

        for (Teacher teacher: teacherList) {
            TeacherInfoDTO teacherInfoDTO = new TeacherInfoDTO();
            teacherInfoDTO.setEmail(teacher.getEmail());

            List<String> studentList = getStudentList(teacher);
            LOGGER.info("Fetched student list of teacher: [{}] studentList: [{}]", teacher.getEmail(), studentList);

            teacherInfoDTO.setStudents(studentList);
            teacherInfoDTOList.add(teacherInfoDTO);
        }

        teachers.setTeachers(teacherInfoDTOList);
        return teachers;
    }

    private List<String> getStudentList(Teacher teacher) {
        List<StudentRegistration> studentRegistrationList = studentRegistrationRepository.findByTeacherAndDeletedFalse(teacher);
        List<String> studentList = new ArrayList<>();
        for (StudentRegistration studentRegistration: studentRegistrationList) {
            studentList.add(studentRegistration.getStudent().getEmail());
        }
        return studentList;
    }

    @Override
    public void createTeacher(TeacherDTO teacherDTO) {
        LOGGER.info("Ready to create a new teacher: [{}]", teacherDTO);
        teacherRepository.save(mapStructMapper.teacherDTOToTeacher(teacherDTO));
        LOGGER.info("Successfully created the teacher. teacher: [{}]", teacherDTO);
    }

    @Override
    public Teacher findByEmail(String email) {
        LOGGER.info("Preparing to find the teacher by email. email: [{}]", email);
        try {
            return teacherRepository.findTeacherByEmail(email);
        } catch (Exception e) {
             throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }


}
