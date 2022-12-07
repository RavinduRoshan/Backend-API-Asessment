package com.example.backendapiasessment.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@EqualsAndHashCode
@ToString
@Setter
@Getter
@Entity
public class Teacher implements Serializable {
    @Id
    private String email;
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "teacher")
    private Set<StudentRegistration> registrations;
}
