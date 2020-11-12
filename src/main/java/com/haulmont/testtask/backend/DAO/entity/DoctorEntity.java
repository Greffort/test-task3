package com.haulmont.testtask.backend.DAO.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "DOCTOR")
public class DoctorEntity {

    private static final String DEFAULT_NAME = "None";
    private static final String DEFAULT_LAST_NAME = "None";
    private static final String DEFAULT_SURNAME = "None";
    private static final String DEFAULT_SPECIALIZATION = "None";

    @Id
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "LASTNAME")
    private String lastName;

    @Column(name = "SURNAME")
    private String surname;

    @Column(name = "SPECIALIZATION")
    private String specialization;

    @JsonIgnore
    @OneToMany(mappedBy = "doctor", fetch = FetchType.EAGER)
    private List<RecipeEntity> recipeEntities;

    public DoctorEntity() {
        this(DEFAULT_NAME);
    }

    public DoctorEntity(String name) {
        this(name, DEFAULT_LAST_NAME);
    }

    public DoctorEntity(String name, String lastName) {
        this(name, lastName, DEFAULT_SURNAME);
    }

    public DoctorEntity(String name, String lastName, String surname) {
        this(name, lastName, surname, DEFAULT_SPECIALIZATION);
    }

    public DoctorEntity(String name, String lastName, String surname, String specialization) {
        this.name = name;
        this.lastName = lastName;
        this.surname = surname;
        this.specialization = specialization;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", surname='" + surname + '\'' +
                ", specialization='" + specialization + '\'' +
                '}';
    }
}
