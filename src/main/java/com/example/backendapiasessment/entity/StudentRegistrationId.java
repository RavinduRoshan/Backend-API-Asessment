package com.example.backendapiasessment.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Embeddable
public class StudentRegistrationId implements Serializable {

    @Column(name = "student_email")
    private String studentEmail;

    @Column(name = "teacher_email")
    private String teacherEmail;
}
