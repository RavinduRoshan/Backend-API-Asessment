package com.example.backendapiasessment.repository;

import com.example.backendapiasessment.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

    Student findStudentByEmail(String email);
}
