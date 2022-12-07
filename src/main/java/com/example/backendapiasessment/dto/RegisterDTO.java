package com.example.backendapiasessment.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode
@ToString
@Setter
@Getter
public class RegisterDTO implements Serializable {

    private String teacher;

    private List<String> students;
}
