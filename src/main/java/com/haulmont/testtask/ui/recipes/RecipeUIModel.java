package com.haulmont.testtask.ui.recipes;

import com.haulmont.testtask.ui.doctors.DoctorUIModel;
import com.haulmont.testtask.ui.patient.PatientUIModel;
import com.haulmont.testtask.ui.recipes.enumerators.PriorityUI;
import lombok.Data;

import java.io.Serializable;

@Data
@SuppressWarnings("serial")
public class RecipeUIModel implements Serializable {

    private Long id;

    private String description;

    private PatientUIModel patient;

    private DoctorUIModel doctor;

    private String createDate;

    private String validity;

    private PriorityUI priority;

}
