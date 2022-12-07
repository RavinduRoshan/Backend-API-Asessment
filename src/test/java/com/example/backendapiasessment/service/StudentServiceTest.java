package com.example.backendapiasessment.service;

import com.example.backendapiasessment.dto.StudentDTO;
import com.example.backendapiasessment.dto.TeacherDTO;
import com.example.backendapiasessment.entity.Student;
import com.example.backendapiasessment.mapper.MapStructMapper;
import com.example.backendapiasessment.repository.StudentRepository;
import com.example.backendapiasessment.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private MapStructMapper mapStructMapper;

    @InjectMocks
    private StudentServiceImpl studentService;

    @BeforeAll
    void init() {
        initMocks(this);
    }

    @Test
    void createStudentTest() {
        Mockito.when(mapStructMapper.studentDTOToStudent(any())).thenReturn(getStudent());
        Mockito.when(studentRepository.save(any())).thenReturn(getStudent());

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setEmail("student1@gmail.com");
        studentDTO.setName("Student1");
        studentService.createStudent(studentDTO);

        verify(studentRepository, times(1)).save(any());
    }

    private Student getStudent() {
        Student student = new Student();
        student.setEmail("student1@gmail.com");
        student.setName("Student1");
        return student;
    }
}
