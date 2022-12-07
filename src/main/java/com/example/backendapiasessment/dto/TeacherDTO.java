package com.example.backendapiasessment.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@EqualsAndHashCode
@ToString
@Setter
@Getter
public class TeacherDTO implements Serializable {

    private String email;
    private String name;
}
