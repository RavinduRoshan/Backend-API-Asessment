package com.example.backendapiasessment.mapper;

import com.example.backendapiasessment.dto.StudentDTO;
import com.example.backendapiasessment.dto.TeacherDTO;
import com.example.backendapiasessment.entity.Student;
import com.example.backendapiasessment.entity.Teacher;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring"
)
public interface MapStructMapper {

    Teacher teacherDTOToTeacher(TeacherDTO teacherDTO);

    Student studentDTOToStudent(StudentDTO studentDTO);
}
