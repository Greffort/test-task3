package com.haulmont.testtask.shared;

/*
 * Class Contains text messages used in other classes
 * @version 12.11.2020
 * Created by Greffort
 */

public class LogMessages {

    public static final String ERROR_PARSING_OBJECT = "\nERROR. Object parsing completed with an error.\n";

    public static final class DoctorServiceImpl {
        public static final String DOCTOR_WAS_ADDED = "The doctor's object was added";
        public static final String DOCTOR_WAS_DELETED = "The doctor's object was deleted";
        public static final String SEARCH_FOR_DOCTOR_BY_NAME = "Find Doctor by Name: ";
        public static final String SEARCH_FOR_DOCTOR_BY_ID = "Find Doctor by ID: ";
        public static final String ALL_DOCTORS_FOUND = "Find all Doctor";
        public static final String DOCTOR_WAS_EDITED = "The doctor's object has been changed";

        public static final String ERROR_DOCTOR_WAS_NOT_ADDED = "ERROR. The doctor's object wasn't added";
        public static final String ERROR_DOCTOR_WAS_NOT_DELETED = "ERROR. The doctor's object wasn't deleted";
        public static final String ERROR_SEARCH_FOR_DOCTOR_BY_NAME = "ERROR. Error finding a Doctor by Name: ";
        public static final String ERROR_SEARCH_FOR_DOCTOR_BY_ID = "ERROR. Error finding a Doctor by ID: ";
        public static final String ERROR_ALL_DOCTORS_FOUND = "ERROR. Error finding all Doctor";
        public static final String ERROR_DOCTOR_WAS_NOT_EDITED = "ERROR. The doctor's object wasn't changed";
    }

    public static final class PatientServiceImpl {
        public static final String PATIENT_WAS_ADDED = "The patient's object was added";
        public static final String PATIENT_WAS_DELETED = "The patient's object was deleted";
        public static final String SEARCH_FOR_PATIENT_BY_NAME = "Find Patient by Name: ";
        public static final String SEARCH_FOR_PATIENT_BY_ID = "Find Patient by ID: ";
        public static final String ALL_PATIENTS_FOUND = "Find all Patient";
        public static final String PATIENT_WAS_EDITED = "The patient's object has been changed";

        public static final String ERROR_PATIENT_WAS_NOT_ADDED = "ERROR. The patient's object wasn't added";
        public static final String ERROR_PATIENT_WAS_NOT_DELETED = "ERROR. The patient's object wasn't deleted";
        public static final String ERROR_SEARCH_FOR_PATIENT_BY_NAME = "ERROR. Error finding a Patient by Name: ";
        public static final String ERROR_SEARCH_FOR_PATIENT_BY_ID = "ERROR. Error finding a Patient by ID: ";
        public static final String ERROR_ALL_PATIENTS_FOUND = "ERROR. Error finding all Patient";
        public static final String ERROR_PATIENT_WAS_NOT_EDITED = "ERROR. The patient's object wasn't changed";
    }

    public static final class RecipeServiceImpl {
        public static final String RECIPE_WAS_ADDED = "The recipe's object was added";
        public static final String RECIPE_WAS_DELETED = "The recipe's object was deleted";
        public static final String SEARCH_FOR_RECIPE_BY_ID = "Find recipe by ID: ";
        public static final String ALL_RECIPES_FOUND = "Find all recipe";
        public static final String RECIPE_WAS_EDITED = "The recipe's object has been changed";

        public static final String FOUND_RECIPE_BY_PATIENT_ID = "Search for a recipe object by PATIENT id";
        public static final String FOUND_RECIPE_BY_DOCTOR_ID = "Search for a recipe object by DOCTOR id";

        public static final String ERROR_FOUND_RECIPE_BY_PATIENT_ID = "No object of recipe by patient id found";
        public static final String ERROR_FOUND_RECIPE_BY_DOCTOR_ID = "No object of recipe by doctor id found";

        public static final String ERROR_RECIPE_WAS_NOT_ADDED = "ERROR. The recipe's object wasn't added";
        public static final String ERROR_SEARCH_FOR_RECIPE_BY_ID = "ERROR. Error finding a recipe by ID: ";
        public static final String ERROR_ALL_RECIPES_FOUND = "ERROR. Error finding all recipe";
        public static final String ERROR_RECIPE_WAS_NOT_EDITED = "ERROR. The recipe's object wasn't changed";
    }

    public static final class Controller {
        public static final String INITIALIZED_APPLICATION_CONTEXT = "Initialized application context";
        public static final String INITIALIZED_CONTROLLER = "Initialized Controller";

        public static final String ERROR_IN_FINDING_ALL_PATIENTS = "Error in finding all patients.";
        public static final String ERROR_IN_FINDING_ALL_DOCTORS = "Error in finding all doctors.";
        public static final String ERROR_IN_FINDING_ALL_RECIPES = "Error in finding all recipes.";
        public static final String AN_ERROR_OCCURRED_WHILE_SEARCHING_FOR_PRESCRIPTIONS_BY_PATIENT_ID = "An error occurred while searching for prescriptions by patient ID.";
        public static final String AN_ERROR_OCCURRED_WHILE_SEARCHING_FOR_PRESCRIPTIONS_BY_DOCTOR_ID = "An error occurred while searching for prescriptions by doctor's ID.";
        public static final String ERROR_ADDING_PATIENT = "Error while adding patient";
        public static final String ERROR_ADDING_DOCTOR = "Error while adding doctor";
        public static final String ERROR_ADDING_RECIPE = "Error while adding recipe";
        public static final String ERROR_EDITED_PATIENT = "Error while changing patient";
        public static final String ERROR_EDITED_DOCTOR = "Error while changing doctor";
        public static final String ERROR_EDITED_RECIPE = "Error while changing recipe";
        public static final String PATIENT_ID_SEARCH_ERROR = "Patient ID Search Error";
        public static final String DOCTOR_ID_SEARCH_ERROR = "Doctor ID Search Error";

    }

    public static final class Notification {
        public static final String SELECT_ITEM = "Please select an item";
        public static final String PATIENT_HAS_RECIPES = "The selected patient has recipes. Removal is not possible.";
        public static final String DOCTOR_HAS_RECIPES = "The selected doctor has recipes. Removal is not possible.";
        public static final String SPECIFIED_ID_IS_BUSY = "The specified ID is busy, please enter another.";
        public static final String SET_FILTER = "Please set filters";
        public static final String PATIENT_NOT_EXIST = "The specified Patient not exist, please enter another.";
        public static final String DOCTOR_NOT_EXIST = "The specified Doctor not exist, please enter another.";
    }
}
