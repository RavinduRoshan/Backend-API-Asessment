package com.example.backendapiasessment.service;

import com.example.backendapiasessment.dto.TeacherDTO;
import com.example.backendapiasessment.dto.TeacherInfoDTO;
import com.example.backendapiasessment.dto.TeachersDTO;
import com.example.backendapiasessment.entity.Student;
import com.example.backendapiasessment.entity.StudentRegistration;
import com.example.backendapiasessment.entity.Teacher;
import com.example.backendapiasessment.mapper.MapStructMapper;
import com.example.backendapiasessment.repository.StudentRegistrationRepository;
import com.example.backendapiasessment.repository.TeacherRepository;
import com.example.backendapiasessment.service.impl.TeacherServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TeacherServiceTest {

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private StudentRegistrationRepository studentRegistrationRepository;

    @Mock
    private MapStructMapper mapStructMapper;

    @InjectMocks
    private TeacherServiceImpl teacherService;

    @BeforeAll
    void init() {
        initMocks(this);
    }

    @Test
    void createTeacherTest() {
        Mockito.when(mapStructMapper.teacherDTOToTeacher(any())).thenReturn(getTeacher());
        Mockito.when(teacherRepository.save(any())).thenReturn(getTeacher());

        TeacherDTO teacherDTO = new TeacherDTO();
        teacherDTO.setEmail("teacher1@gmail.com");
        teacherDTO.setName("Teacher1");
        teacherService.createTeacher(teacherDTO);

        verify(teacherRepository, times(1)).save(any());
    }

    @Test
    void getAllTeachersTest() {
        Mockito.when(studentRegistrationRepository.findByTeacherAndDeletedFalse(any())).thenReturn(getStudentRegistrations());
        Mockito.when(teacherRepository.findAll()).thenReturn(getTeacherList());
        TeachersDTO allTeachers = teacherService.getAllTeachers();
        Assert.assertEquals(1, allTeachers.getTeachers().size());

        TeacherInfoDTO teacherInfoDTO = allTeachers.getTeachers().get(0);
        Assert.assertEquals(1, teacherInfoDTO.getStudents().size());
        Assert.assertEquals("teacher1@gmail.com", teacherInfoDTO.getEmail());
        Assert.assertEquals("student1@gmail.com", teacherInfoDTO.getStudents().get(0));
    }

    private List<Teacher> getTeacherList() {
        List<Teacher> teachers = new ArrayList<>();
        teachers.add(getTeacher());
        return teachers;
    }

    private List<StudentRegistration> getStudentRegistrations() {
        List<StudentRegistration> studentRegistrationList = new ArrayList<>();

        StudentRegistration studentRegistration1 = new StudentRegistration();
        studentRegistration1.setStudent(getStudent());
        studentRegistration1.setTeacher(getTeacher());
        studentRegistrationList.add(studentRegistration1);

        return studentRegistrationList;
    }

    private Student getStudent() {
        Student student = new Student();
        student.setEmail("student1@gmail.com");
        student.setName("Student1");
        return student;
    }

    private Teacher getTeacher() {
        Teacher teacher = new Teacher();
        teacher.setEmail("teacher1@gmail.com");
        teacher.setName("Teacher1");
        return teacher;
    }
}