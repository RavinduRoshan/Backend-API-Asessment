package com.example.backendapiasessment.service;

import com.example.backendapiasessment.dto.CommonStudentsDTO;
import com.example.backendapiasessment.dto.DeRegisterDTO;
import com.example.backendapiasessment.dto.RegisterDTO;
import com.example.backendapiasessment.entity.Student;
import com.example.backendapiasessment.entity.StudentRegistration;
import com.example.backendapiasessment.entity.Teacher;
import com.example.backendapiasessment.exception.ApiException;
import com.example.backendapiasessment.repository.StudentRegistrationRepository;
import com.example.backendapiasessment.service.impl.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StudentRegistrationTest {

    @Mock
    private StudentService studentService;

    @Mock
    private TeacherService teacherService;

    @Mock
    private StudentRegistrationRepository studentRegistrationRepository;

    @InjectMocks
    private RegistrationServiceImpl registrationService;

    @BeforeEach
    void init() {
        initMocks(this);
    }

    @Test
    void registerTest() {
        Teacher teacher = getTeacher();
        Student student1 = getStudent("student1@gmail.com", "Student1");
        Student student2 = getStudent("student2@gmail.com", "Student2");

        Mockito.when(teacherService.findByEmail("teacher1@gmail.com")).thenReturn(teacher);
        Mockito.when(studentService.findByEmail("student1@gmail.com")).thenReturn(student1);
        Mockito.when(studentService.findByEmail("student2@gmail.com")).thenReturn(student2);
        Mockito.when(studentRegistrationRepository.findByTeacherAndStudentAndDeletedFalse(any(), any())).thenReturn(null);
        Mockito.when(studentRegistrationRepository.save(any())).thenReturn(getStudentRegistration(teacher, student1));

        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setTeacher("teacher1@gmail.com");
        registerDTO.setStudents(List.of("student1@gmail.com", "student2@gmail.com"));
        registrationService.register(registerDTO);

        verify(teacherService, times(1)).findByEmail(any());
        verify(studentService, times(2)).findByEmail(any());
        verify(studentRegistrationRepository, times(2)).findByTeacherAndStudentAndDeletedFalse(any(), any());
        verify(studentRegistrationRepository, times(2)).save(any());
    }

    @Test
    void registerTestForAlreadyRegisteredStudent() {
        Teacher teacher = getTeacher();
        Student student1 = getStudent("student1@gmail.com", "Student1");

        Mockito.when(teacherService.findByEmail("teacher1@gmail.com")).thenReturn(teacher);
        Mockito.when(studentService.findByEmail("student1@gmail.com")).thenReturn(student1);
        Mockito.when(studentRegistrationRepository.findByTeacherAndStudentAndDeletedFalse(teacher, student1))
                .thenReturn(getStudentRegistration(teacher, student1));

        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setTeacher("teacher1@gmail.com");
        registerDTO.setStudents(List.of("student1@gmail.com"));
        Exception exception = assertThrows(ApiException.class, () -> registrationService.register(registerDTO));

        assertEquals("Student is already registered to this teacher.", exception.getMessage());

        verify(teacherService, times(1)).findByEmail(any());
        verify(studentService, times(1)).findByEmail(any());
        verify(studentRegistrationRepository, times(1)).findByTeacherAndStudentAndDeletedFalse(any(), any());
        verify(studentRegistrationRepository, times(0)).save(any());
    }

    @Test
    void deregisterTest() {
        Teacher teacher = getTeacher();
        Student student1 = getStudent("student1@gmail.com", "Student1");

        Mockito.when(teacherService.findByEmail("teacher1@gmail.com")).thenReturn(teacher);
        Mockito.when(studentService.findByEmail("student1@gmail.com")).thenReturn(student1);
        Mockito.when(studentRegistrationRepository.findByTeacherAndStudentAndDeletedFalse(any(), any()))
                .thenReturn(getStudentRegistration(teacher, student1));
        Mockito.when(studentRegistrationRepository.save(any())).thenReturn(getStudentRegistration(teacher, student1));

        DeRegisterDTO deRegisterDTO = new DeRegisterDTO();
        deRegisterDTO.setTeacher("teacher1@gmail.com");
        deRegisterDTO.setStudent("student1@gmail.com");
        registrationService.deregister(deRegisterDTO);

        verify(teacherService, times(1)).findByEmail(any());
        verify(studentService, times(1)).findByEmail(any());
        verify(studentRegistrationRepository, times(1)).findByTeacherAndStudentAndDeletedFalse(any(), any());
        verify(studentRegistrationRepository, times(1)).save(any());
    }

    @Test
    void deregisterTestForUnRegisteredStudent() {
        Teacher teacher = getTeacher();
        Student student1 = getStudent("student1@gmail.com", "Student1");

        Mockito.when(teacherService.findByEmail("teacher1@gmail.com")).thenReturn(teacher);
        Mockito.when(studentService.findByEmail("student1@gmail.com")).thenReturn(student1);
        Mockito.when(studentRegistrationRepository.findByTeacherAndStudentAndDeletedFalse(any(), any())).thenReturn(null);
        Mockito.when(studentRegistrationRepository.save(any())).thenReturn(getStudentRegistration(teacher, student1));

        DeRegisterDTO deRegisterDTO = new DeRegisterDTO();
        deRegisterDTO.setTeacher("teacher1@gmail.com");
        deRegisterDTO.setStudent("student1@gmail.com");


        Exception exception = assertThrows(ApiException.class, () -> registrationService.deregister(deRegisterDTO));
        assertEquals("Student is not registered to this teacher.", exception.getMessage());

        verify(teacherService, times(1)).findByEmail(any());
        verify(studentService, times(1)).findByEmail(any());
        verify(studentRegistrationRepository, times(1)).findByTeacherAndStudentAndDeletedFalse(any(), any());
        verify(studentRegistrationRepository, times(0)).save(any());
    }

    @Test
    void getCommonStudentsTest() {
        Mockito.when(studentRegistrationRepository.findByTeacherAndDeletedFalse(any())).thenReturn(getStudentRegistrationsForTeacher1());

        List<String> teachers = new ArrayList<>();
        teachers.add("teacher1@gmail.com");
        CommonStudentsDTO commonStudents = registrationService.getCommonStudents(teachers);

        assertEquals(2, commonStudents.getStudents().size());
        assertEquals("student1@gmail.com", commonStudents.getStudents().get(0));
        assertEquals("student2@gmail.com", commonStudents.getStudents().get(1));
    }

    private Teacher getTeacher() {
        Teacher teacher = new Teacher();
        teacher.setEmail("teacher1@gmail.com");
        teacher.setName("Teacher1");
        return teacher;
    }

    private Student getStudent(String email, String name) {
        Student student = new Student();
        student.setEmail(email);
        student.setName(name);
        return student;
    }

    private StudentRegistration getStudentRegistration(Teacher teacher, Student student) {
        StudentRegistration studentRegistration1 = new StudentRegistration();
        studentRegistration1.setStudent(student);
        studentRegistration1.setTeacher(teacher);
        return studentRegistration1;
    }

    private List<StudentRegistration> getStudentRegistrationsForTeacher1() {
        List<StudentRegistration> studentRegistrationList = new ArrayList<>();
        Teacher teacher1 = getTeacher();

        Student student1 = getStudent("student1@gmail.com", "Student1");
        Student student2 = getStudent("student2@gmail.com", "Student2");

        StudentRegistration studentRegistration1 = getStudentRegistration(teacher1, student1);
        StudentRegistration studentRegistration2 = getStudentRegistration(teacher1, student2);

        studentRegistrationList.add(studentRegistration1);
        studentRegistrationList.add(studentRegistration2);

        return studentRegistrationList;
    }
}