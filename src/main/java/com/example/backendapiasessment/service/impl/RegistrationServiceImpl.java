package com.example.backendapiasessment.service.impl;

import com.example.backendapiasessment.dto.CommonStudentsDTO;
import com.example.backendapiasessment.dto.DeRegisterDTO;
import com.example.backendapiasessment.dto.RegisterDTO;
import com.example.backendapiasessment.entity.Student;
import com.example.backendapiasessment.entity.StudentRegistration;
import com.example.backendapiasessment.entity.Teacher;
import com.example.backendapiasessment.exception.ApiException;
import com.example.backendapiasessment.repository.StudentRegistrationRepository;
import com.example.backendapiasessment.service.RegistrationService;
import com.example.backendapiasessment.service.StudentService;
import com.example.backendapiasessment.service.TeacherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationServiceImpl.class);

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRegistrationRepository studentRegistrationRepository;

    @Override
    public void register(RegisterDTO registerDTO) throws ApiException {
        LOGGER.info("Preparing to register a student. request: [{}]", registerDTO);
        Teacher teacher = teacherService.findByEmail(registerDTO.getTeacher());
        validateTeacher(teacher);

        for (String studentEmail: registerDTO.getStudents()) {
            Student student = studentService.findByEmail(studentEmail);
            validateStudent(student);

            StudentRegistration existingRegistration = getActiveStudentRegistration(teacher, student);
            validateExistingRegistration(existingRegistration);

            persistRegistration(teacher, student);
        }
    }

    private StudentRegistration getActiveStudentRegistration(Teacher teacher, Student student) {
        try {
            return studentRegistrationRepository.findByTeacherAndStudentAndDeletedFalse(teacher, student);
        } catch (Exception e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private void persistRegistration(Teacher teacher, Student student) {
        try {
            StudentRegistration studentRegistration = new StudentRegistration();
            studentRegistration.setStudent(student);
            studentRegistration.setTeacher(teacher);
            studentRegistration.setDeleted(false);
            studentRegistrationRepository.save(studentRegistration);
            LOGGER.info("Student registration is success. teacher: [{}] student: [{}]", teacher.getEmail(), student.getEmail());
        } catch (Exception e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private void persistDeRegistration(StudentRegistration studentRegistration, String reason) {
        try {
            studentRegistration.setDeleted(true);
            studentRegistration.setReason(reason);
            studentRegistrationRepository.save(studentRegistration);
            LOGGER.info("Student de registration is success.");
        } catch (Exception e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public void deregister(DeRegisterDTO deRegisterDTO) throws ApiException {
        LOGGER.info("Preparing to de-register a student. request: [{}]", deRegisterDTO);
        Teacher teacher = teacherService.findByEmail(deRegisterDTO.getTeacher());
        Student student = studentService.findByEmail(deRegisterDTO.getStudent());

        StudentRegistration existingRegistration = getActiveStudentRegistration(teacher, student);

        if (existingRegistration != null) {
            persistDeRegistration(existingRegistration, deRegisterDTO.getReason());
        } else {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Student is not registered to this teacher.");
        }
    }

    @Override
    public CommonStudentsDTO getCommonStudents(List<String> teachers) throws ApiException {
        List<List<String>> studentCollection = getStudentCollection(teachers);
        CommonStudentsDTO commonStudentsDTO = new CommonStudentsDTO();

        if (!studentCollection.isEmpty()) {
            List<String> students = extractCommonStudents(studentCollection);
            commonStudentsDTO.setStudents(students);
        }

        return commonStudentsDTO;
    }

    private List<StudentRegistration> getStudentsByTeacher(String email) throws ApiException {
        Teacher teacher = new Teacher();
        teacher.setEmail(email);

        try {
            return studentRegistrationRepository.findByTeacherAndDeletedFalse(teacher);
        } catch (Exception e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private List<List<String>> getStudentCollection(List<String> teachers) {
        List<List<String>> studentCollection = new ArrayList<>();
        for (String teacher: teachers) {
            List<String> students = getStudentsByTeacher(teacher).stream()
                    .map(studentRegistration -> studentRegistration.getStudent().getEmail())
                    .collect(Collectors.toList());
            studentCollection.add(students);
        }

        return studentCollection;
    }

    private List<String> extractCommonStudents(List<List<String>> studentCollection) {
        List<String> students = studentCollection.get(0);
        for (int i = 1; i < studentCollection.size(); i++) {
            students.retainAll(studentCollection.get(i));
        }

        return students;
    }

    private void validateTeacher(Teacher teacher) {
        if (teacher == null) {
            LOGGER.error("Teacher cannot be found.");
            throw new ApiException(HttpStatus.BAD_REQUEST, "Teacher cannot be found.");
        }
    }

    private void validateStudent(Student student) {
        if (student == null) {
            LOGGER.error("Student cannot be found.");
            throw new ApiException(HttpStatus.BAD_REQUEST, "Student cannot be found.");
        }
    }

    private void validateExistingRegistration(StudentRegistration registration) {
        if (registration != null) {
            LOGGER.error("Student is already registered to this teacher. student: [{}] teacher: [{}]",
                    registration.getStudent().getEmail(), registration.getTeacher().getEmail());
            throw new ApiException(HttpStatus.BAD_REQUEST, "Student is already registered to this teacher.");
        }
    }
}
