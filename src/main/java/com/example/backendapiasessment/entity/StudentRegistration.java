package com.example.backendapiasessment.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
public class StudentRegistration implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch= FetchType.LAZY)
    //@MapsId("studentEmail")
    @JoinColumn(name = "student_email")
    private Student student;

    @ManyToOne(fetch= FetchType.LAZY)
    //@MapsId("teacherEmail")
    @JoinColumn(name = "teacher_email")
    private Teacher teacher;

    private Boolean deleted = false;
    private String reason;
}
