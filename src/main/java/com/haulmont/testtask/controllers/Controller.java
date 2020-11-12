package com.haulmont.testtask.controllers;

import com.haulmont.testtask.backend.DAO.service.DoctorService;
import com.haulmont.testtask.backend.DAO.service.PatientService;
import com.haulmont.testtask.backend.DAO.service.RecipeService;
import com.haulmont.testtask.backend.model.Doctor;
import com.haulmont.testtask.backend.model.Patient;
import com.haulmont.testtask.backend.model.Recipe;
import com.haulmont.testtask.shared.LoggerHelper;
import com.haulmont.testtask.shared.Mapper;
import com.haulmont.testtask.ui.doctors.DoctorUIModel;
import com.haulmont.testtask.ui.patient.PatientUIModel;
import com.haulmont.testtask.ui.recipes.RecipeUIModel;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.haulmont.testtask.shared.LogMessages.Controller.*;
import static com.haulmont.testtask.shared.LogMessages.ERROR_PARSING_OBJECT;

/*
 * The class contains the logic for the interaction of the graphical view with the backend layer.
 * @version 12.11.2020
 * Created by Greffort
 */

public class Controller implements Serializable {

    private static final Logger logFile = Logger.getLogger("APP2");

    private DoctorService doctorService;
    private PatientService patientService;
    private RecipeService recipeService;
    private static Controller CONTROLLER;

    private Controller() {
    }

    public void init(@NotNull ApplicationContext applicationContext) {
        doctorService = applicationContext.getBean(DoctorService.class);
        patientService = applicationContext.getBean(PatientService.class);
        recipeService = applicationContext.getBean(RecipeService.class);

        LoggerHelper.info(doctorService.toString() + patientService.toString(), logFile);
        LoggerHelper.info(INITIALIZED_APPLICATION_CONTEXT, logFile);
    }

    public static synchronized Controller instance() {
        if (CONTROLLER == null) {
            CONTROLLER = new Controller();
            LoggerHelper.info(INITIALIZED_CONTROLLER, logFile);
        }
        return CONTROLLER;
    }

    public List<PatientUIModel> findAllPatients() {
        List<Patient> patients = patientService.findAll();
        List<PatientUIModel> patientUIModels = new ArrayList<>();

        if (patients != null) {
            for (Patient patient : patients) {
                try {
                    patientUIModels.add(Mapper.toJavaObject(patient, PatientUIModel.class));
                } catch (IOException e) {
                    LoggerHelper.error(ERROR_PARSING_OBJECT + ERROR_IN_FINDING_ALL_PATIENTS, e, logFile);
                    return new ArrayList<>();
                }
            }
        }

        return patientUIModels;
    }

    public List<DoctorUIModel> findAllDoctors() {
        List<Doctor> doctors = doctorService.findAll();
        List<DoctorUIModel> doctorUIModels = new ArrayList<>();

        if (doctors != null) {
            for (Doctor doctor : doctors) {
                try {
                    doctorUIModels.add(Mapper.toJavaObject(doctor, DoctorUIModel.class));
                } catch (IOException e) {
                    LoggerHelper.error(ERROR_PARSING_OBJECT + ERROR_IN_FINDING_ALL_DOCTORS, e, logFile);
                    return new ArrayList<>();
                }
            }
        }

        return doctorUIModels;
    }

    public List<RecipeUIModel> findAllRecipes() {
        List<Recipe> recipes = recipeService.findAll();
        List<RecipeUIModel> recipeUIModels = new ArrayList<>();

        if (recipes != null) {
            for (Recipe recipe : recipes) {
                try {
                    recipeUIModels.add(Mapper.toJavaObject(recipe, RecipeUIModel.class));
                } catch (IOException e) {
                    LoggerHelper.error(ERROR_PARSING_OBJECT + ERROR_IN_FINDING_ALL_RECIPES, e, logFile);
                    return new ArrayList<>();
                }
            }
        }

        return recipeUIModels;
    }

    public void deletePatientByID(@NotNull Long patientID) {
        patientService.deleteById(patientID);
    }

    public void deleteDoctorByID(@NotNull Long doctorID) {
        doctorService.deleteById(doctorID);
    }

    public void deleteRecipeByID(@NotNull Long recipeID) {
        recipeService.deleteById(recipeID);
    }

    public boolean findRecipeByPatientOrderById(PatientUIModel patientUIModel) {
        try {
            return recipeService.findRecipeByPatientOrderById(Mapper.toJavaObject(patientUIModel, Patient.class));
        } catch (IOException e) {
            LoggerHelper.error(ERROR_PARSING_OBJECT + AN_ERROR_OCCURRED_WHILE_SEARCHING_FOR_PRESCRIPTIONS_BY_PATIENT_ID, e, logFile);
            return false;
        }
    }

    public boolean findRecipeByDoctorOrderById(DoctorUIModel doctorUIModel) {
        try {
            return recipeService.findRecipeByDoctorOrderById(Mapper.toJavaObject(doctorUIModel, Doctor.class));
        } catch (IOException e) {
            LoggerHelper.error(ERROR_PARSING_OBJECT + AN_ERROR_OCCURRED_WHILE_SEARCHING_FOR_PRESCRIPTIONS_BY_DOCTOR_ID, e, logFile);
            return false;
        }
    }

    public List<RecipeUIModel> findRecipeByDoctorId(DoctorUIModel doctorUIModel) {
        try {
            List<Recipe> recipes = recipeService.findRecipeByDoctorId(Mapper.toJavaObject(doctorUIModel, Doctor.class));
            List<RecipeUIModel> recipeUIModels = new ArrayList<>();

            for (Recipe recipe : recipes) {
                recipeUIModels.add(Mapper.toJavaObject(recipe, RecipeUIModel.class));
            }
            return recipeUIModels;
        } catch (IOException e) {
            LoggerHelper.error(ERROR_PARSING_OBJECT + AN_ERROR_OCCURRED_WHILE_SEARCHING_FOR_PRESCRIPTIONS_BY_DOCTOR_ID, e, logFile);
            return null;
        }
    }

    public void addPatient(PatientUIModel patientUIModel) {
        try {
            patientService.addPatient(Mapper.toJavaObject(patientUIModel, Patient.class));
        } catch (IOException e) {
            LoggerHelper.error(ERROR_PARSING_OBJECT + ERROR_ADDING_PATIENT, e, logFile);
            e.printStackTrace();
        }
    }

    public void addDoctor(DoctorUIModel doctorUIModel) {
        try {
            doctorService.addDoctor(Mapper.toJavaObject(doctorUIModel, Doctor.class));
        } catch (IOException e) {
            LoggerHelper.error(ERROR_PARSING_OBJECT + ERROR_ADDING_DOCTOR, e, logFile);
        }
    }

    public void addRecipe(RecipeUIModel recipeUIModel) {
        try {
            recipeService.addRecipe(Mapper.toJavaObject(recipeUIModel, Recipe.class));
        } catch (IOException e) {
            LoggerHelper.error(ERROR_PARSING_OBJECT + ERROR_ADDING_RECIPE, e, logFile);
        }
    }

    public boolean checkIDForPatient(@NotNull PatientUIModel patientUIModel) {
        return patientService.findById(patientUIModel.getId()) != null;
    }

    public boolean checkIDForDoctor(@NotNull DoctorUIModel doctorUIModel) {
        return doctorService.findById(doctorUIModel.getId()) != null;
    }

    public PatientUIModel findPatientByID(@NotNull Long id) {
        try {
            return Mapper.toJavaObject(patientService.findById(id), PatientUIModel.class);
        } catch (IOException e) {
            LoggerHelper.error(ERROR_PARSING_OBJECT + PATIENT_ID_SEARCH_ERROR, e, logFile);
            return null;
        }
    }

    public DoctorUIModel findDoctorByID(@NotNull Long id) {
        try {
            return Mapper.toJavaObject(doctorService.findById(id), DoctorUIModel.class);
        } catch (IOException e) {
            LoggerHelper.error(ERROR_PARSING_OBJECT + DOCTOR_ID_SEARCH_ERROR, e, logFile);
            return null;
        }
    }


    public boolean checkIDForRecipe(@NotNull RecipeUIModel recipeUIModel) {
        return recipeService.findById(recipeUIModel.getId()) != null;
    }

    public void editPatient(@NotNull PatientUIModel patientUIModel) {
        try {
            if (checkIDForPatient(patientUIModel)) {
                patientService.editPatient(Mapper.toJavaObject(patientUIModel, Patient.class));
            }
        } catch (IOException e) {
            LoggerHelper.error(ERROR_PARSING_OBJECT + ERROR_EDITED_PATIENT, e, logFile);
        }
    }

    public void editDoctor(@NotNull DoctorUIModel doctorUIModel) {
        try {
            if (checkIDForDoctor(doctorUIModel)) {
                doctorService.editDoctor(Mapper.toJavaObject(doctorUIModel, Doctor.class));
            }
        } catch (IOException e) {
            LoggerHelper.error(ERROR_PARSING_OBJECT + ERROR_EDITED_DOCTOR, e, logFile);
        }
    }

    public void editRecipe(@NotNull RecipeUIModel recipeUIModel) {
        try {
            if (checkIDForRecipe(recipeUIModel)) {
                recipeService.editRecipe(Mapper.toJavaObject(recipeUIModel, Recipe.class));
            }
        } catch (IOException e) {
            LoggerHelper.error(ERROR_PARSING_OBJECT + ERROR_EDITED_RECIPE, e, logFile);
        }
    }
}
