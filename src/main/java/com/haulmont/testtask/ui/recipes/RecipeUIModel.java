package com.haulmont.testtask.ui.recipes;

import com.haulmont.testtask.ui.doctors.DoctorModelUI;
import com.haulmont.testtask.ui.patient.PatientUIModel;
import com.haulmont.testtask.ui.recipes.enumerators.PriorityUI;
import lombok.Data;

import java.io.Serializable;

/*
 * The class defines a recipe object in a format suitable for UI
 * @version 12.11.2020
 * Created by Greffort
 */

@Data
@SuppressWarnings("serial")
public class RecipeUIModel implements Serializable {

    private Long id;

    private String description;

    private PatientUIModel patient;

    private DoctorModelUI doctor;

    private String createDate;

    private String validity;

    private PriorityUI priority;

}
