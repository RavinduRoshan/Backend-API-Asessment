package com.example.backendapiasessment.repository;

import com.example.backendapiasessment.entity.Student;
import com.example.backendapiasessment.entity.StudentRegistration;
import com.example.backendapiasessment.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRegistrationRepository extends JpaRepository<StudentRegistration, Long> {

    StudentRegistration findByTeacherAndStudent(Teacher teacher, Student student);

    StudentRegistration findByTeacherAndStudentAndDeletedFalse(Teacher teacher, Student student);

    List<StudentRegistration> findByTeacherAndDeletedFalse(Teacher teacher);
}
