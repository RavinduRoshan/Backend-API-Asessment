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
public class DeRegisterDTO implements Serializable {

    private String teacher;

    private String student;

    private String reason;
}
