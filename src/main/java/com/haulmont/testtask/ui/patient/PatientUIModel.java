package com.haulmont.testtask.ui.patient;

import lombok.Data;

import java.io.Serializable;

@Data
@SuppressWarnings("serial")
public class PatientUIModel implements Serializable {

    private Long id;

    private String name;

    private String lastName;

    private String surname;

    private String phoneNumber;

    @Override
    public String toString() {
        return id + "";
    }

}
