package com.haulmont.testtask.ui.patient;

import lombok.Data;

import java.io.Serializable;

/*
 * The class defines a patient object in a format suitable for UI
 * @version 12.11.2020
 * Created by Aleksandr Kravchina
 */

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
