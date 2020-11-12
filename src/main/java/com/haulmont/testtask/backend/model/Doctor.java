package com.haulmont.testtask.backend.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Doctor implements Serializable {

    private static final String DEFAULT_NAME = "None";
    private static final String DEFAULT_LAST_NAME = "None";
    private static final String DEFAULT_SURNAME = "None";
    private static final String DEFAULT_SPECIALIZATION = "None";

    private Long id;
    private String name;
    private String lastName;
    private String surname;
    private String specialization;

    public Doctor() {
        this(DEFAULT_NAME);
    }

    public Doctor(String name) {
        this(name, DEFAULT_LAST_NAME);
    }

    public Doctor(String name, String lastName) {
        this(name, lastName, DEFAULT_SURNAME);
    }

    public Doctor(String name, String lastName, String surname) {
        this(name, lastName, surname, DEFAULT_SPECIALIZATION);
    }

    public Doctor(String name, String lastName, String surname, String specialization) {
        this.name = name;
        this.lastName = lastName;
        this.surname = surname;
        this.specialization = specialization;
    }
}
