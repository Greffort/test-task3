package com.haulmont.testtask.backend.DAO.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/*
 * Class defines a database entity Patient
 * @version 12.11.2020
 * Created by Greffort
 */

@Data
@Entity
@Table(name = "PATIENT")
public class PatientEntity {

    private static final String DEFAULT_NAME = "None";
    private static final String DEFAULT_LAST_NAME = "None";
    private static final String DEFAULT_SURNAME = "None";
    private static final String DEFAULT_PHONE_NUMBER = "None";

    @Id
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "LASTNAME")
    private String lastName;

    @Column(name = "SURNAME")
    private String surname;

    @Column(name = "PHONENUMBER")
    private String phoneNumber;

    @JsonIgnore
    @OneToMany(mappedBy = "patient", fetch = FetchType.EAGER)
    private List<RecipeEntity> recipeEntities;

    public PatientEntity() {
        this(DEFAULT_NAME);
    }

    public PatientEntity(String name) {
        this(name, DEFAULT_LAST_NAME);
    }

    public PatientEntity(String name, String lastName) {
        this(name, lastName, DEFAULT_SURNAME);
    }

    public PatientEntity(String name, String lastName, String surname) {
        this(name, lastName, surname, DEFAULT_PHONE_NUMBER);
    }

    public PatientEntity(String name, String lastName, String surname, String phoneNumber) {
        this.name = name;
        this.lastName = lastName;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", surname='" + surname + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
