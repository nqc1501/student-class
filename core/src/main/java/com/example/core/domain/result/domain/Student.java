package com.example.core.domain.result.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "students")
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String birthday;

    private String email;

    private String phone;

    @Column(name = "student_code")
    private String student_code;

    @Column(name = "class")
    @JsonProperty(value = "class")
    private Integer studyClass;

    private String image;

    private Integer gender;

    @Column(name = "ability_to_borrow")
    private Integer abilityToBorrow;
}