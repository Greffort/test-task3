package com.haulmont.testtask.backend.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Patient implements Serializable {

    private static final String DEFAULT_NAME = "None";
    private static final String DEFAULT_LAST_NAME = "None";
    private static final String DEFAULT_SURNAME = "None";
    private static final String DEFAULT_PHONE_NUMBER = "None";

    private Long id;
    private String name;
    private String lastName;
    private String surname;
    private String phoneNumber;

    public Patient() {
        this(DEFAULT_NAME);
    }

    public Patient(String name) {
        this(name, DEFAULT_LAST_NAME);
    }

    public Patient(String name, String lastName) {
        this(name, lastName, DEFAULT_SURNAME);
    }

    public Patient(String name, String lastName, String surname) {
        this(name, lastName, surname, DEFAULT_PHONE_NUMBER);
    }

    public Patient(String name, String lastName, String surname, String phoneNumber) {
        this.name = name;
        this.lastName = lastName;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
    }
}
