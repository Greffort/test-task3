package com.haulmont.testtask.backend.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/*
 * Defines a Doctor object.
 * @version 12.11.2020
 * Created by Greffort
 */

@Data
public class Doctor implements Serializable {

    private static final String DEFAULT = "None";

    @Autowired
    private Long id;

    private String name;
    private String lastName;
    private String surname;
    private String specialization;

    public Doctor(String name) {
        this(name, DEFAULT);
    }

    public Doctor(String name, String lastName) {
        this(name, lastName, DEFAULT);
    }

    public Doctor(String name, String lastName, String surname) {
        this(name, lastName, surname, DEFAULT);
    }

    public Doctor(String name, String lastName, String surname, String specialization) {
        this.name = name;
        this.lastName = lastName;
        this.surname = surname;
        this.specialization = specialization;

        StringBuilder
    }

    public Doctor() {
        this(DEFAULT);
    }
}
