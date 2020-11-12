package com.haulmont.testtask.ui.recipes;

import com.haulmont.testtask.controllers.Controller;
import com.haulmont.testtask.ui.doctors.DoctorUIModel;
import com.haulmont.testtask.ui.patient.PatientUIModel;
import com.haulmont.testtask.ui.recipes.enumerators.PriorityUI;
import com.vaadin.data.ValidationResult;
import com.vaadin.data.Validator;
import com.vaadin.data.ValueContext;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.UserError;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.time.LocalDate;

import static com.haulmont.testtask.shared.LogMessages.Notification.*;

/*
 * Class defines the form for adding a Recipe
 * @version 12.11.2020
 * Created by Aleksandr Kravchina
 */

public class AddRecipeView extends FormLayout {

    private Boolean isCorrectlyID;

    private Boolean isCorrectlyPatientID;
    private Boolean isCorrectlyDoctorID;

    private TextField id;
    private TextField description;
    private TextField patient;
    private TextField doctor;
    private DateField createDate;
    private DateField validity;
    private ComboBox<PriorityUI> priority;

    private Button save;
    private Button cancel;

    private RecipeView view;

    public AddRecipeView(RecipeView view) {
        this.view = view;
        setupLayout();
        addValidators();
        addListenerForButtons();
    }

    private void setupLayout() {
        save = new Button("Save");
        cancel = new Button("Cancel");
        this.isCorrectlyID = false;
        this.isCorrectlyPatientID = false;
        this.isCorrectlyDoctorID = false;
        save.setVisible(false);

        id = new TextField("ID");
        description = new TextField("Description");
        patient = new TextField("Patient ID");
        doctor = new TextField("Doctor ID");
        createDate = new DateField("Createdate");
        validity = new DateField("Validity");
        priority = new ComboBox<>("Priority");

        createDate.setValue(LocalDate.now());
        validity.setValue(LocalDate.now().plusDays(3L));

        priority.setItems(PriorityUI.NORMAL,
                PriorityUI.CITO,
                PriorityUI.STATIM);
        priority.setValue(PriorityUI.NORMAL);
        setSizeUndefined();
        HorizontalLayout buttons = new HorizontalLayout(save, cancel);
        addComponents(id, description, patient, doctor,
                createDate, validity, priority, buttons);
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(KeyCode.ENTER);
    }

    private void addListenerForButtons() {
        save.addClickListener(e -> this.save());
        cancel.addClickListener(e -> this.cancel());
    }

    private void addValidators() {
        Validator validatorID = new RegexpValidator("Not valid",
                "^[0-9]+$",
                true);
        id.addValueChangeListener(event -> {
            ValidationResult result = validatorID.apply(event.getValue(),
                    new ValueContext(id));

            if (result.isError()) {
                UserError error = new UserError(result.getErrorMessage());
                id.setComponentError(error);
                save.setVisible(false);
            } else {
                id.setComponentError(null);
                isCorrectlyID = true;
                checkCorrectlyTextFields();
            }
        });

        Validator validatorDoctorID = new RegexpValidator("Not valid",
                "^[0-9]+$",
                true);
        doctor.addValueChangeListener(event -> {
            ValidationResult result = validatorDoctorID.apply(event.getValue(),
                    new ValueContext(id));
            if (result.isError()) {
                UserError error = new UserError(result.getErrorMessage());
                doctor.setComponentError(error);
                save.setVisible(false);
            } else {
                doctor.setComponentError(null);
                isCorrectlyDoctorID = true;
                checkCorrectlyTextFields();
            }
        });

        Validator validatorPatientID = new RegexpValidator("Not valid",
                "^[0-9]+$",
                true);
        patient.addValueChangeListener(event -> {
            ValidationResult result = validatorPatientID.apply(event.getValue(),
                    new ValueContext(id));
            if (result.isError()) {
                UserError error = new UserError(result.getErrorMessage());
                patient.setComponentError(error);
                save.setVisible(false);
            } else {
                patient.setComponentError(null);
                isCorrectlyPatientID = true;
                checkCorrectlyTextFields();
            }
        });
    }

    private void checkCorrectlyTextFields() {
        if (isCorrectlyID && isCorrectlyDoctorID && isCorrectlyPatientID) {
            save.setVisible(true);
        }
    }

    private void cancel() {
        cleanForm();
        setVisible(false);
    }

    private boolean checkDoctorIsExist(Long id) {
        if (getPatientByID(id) != null) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkPatientIsExist(Long id) {
        if (getDoctorByID(id) != null) {
            return true;
        } else {
            return false;
        }
    }

    private void save() {
        if (checkDoctorIsExist(Long.valueOf(doctor.getValue()))) {
            if (checkPatientIsExist(Long.valueOf(patient.getValue()))) {
                RecipeUIModel recipeUIModel = new RecipeUIModel();

                recipeUIModel.setId(Long.valueOf(this.id.getValue()));
                recipeUIModel.setDescription(this.description.getValue());
                recipeUIModel.setPatient(getPatientByID(Long.valueOf(patient.getValue())));
                recipeUIModel.setDoctor(getDoctorByID(Long.valueOf(doctor.getValue())));
                recipeUIModel.setCreateDate(String.valueOf(this.createDate.getValue()));
                recipeUIModel.setValidity(String.valueOf(this.validity.getValue()));
                recipeUIModel.setPriority(this.priority.getValue());

                if (Controller.instance().checkIDForRecipe(recipeUIModel)) {
                    Notification.show(SPECIFIED_ID_IS_BUSY);
                } else {
                    Controller.instance().addRecipe(recipeUIModel);
                    view.updateList();
                    cleanForm();
                }
            } else {
                Notification.show(PATIENT_NOT_EXIST);
            }
        } else {
            Notification.show(DOCTOR_NOT_EXIST);
        }
    }

    private DoctorUIModel getDoctorByID(Long id) {
        return Controller.instance().findDoctorByID(id);
    }

    private PatientUIModel getPatientByID(Long id) {
        return Controller.instance().findPatientByID(id);
    }

    private void cleanForm() {
        this.isCorrectlyID = false;
        this.id.setValue("");

        this.id.setValue("");
        this.description.setValue("");
        this.patient.setValue("");
        this.doctor.setValue("");
        this.createDate.setValue(LocalDate.now());
        this.validity.setValue(LocalDate.now().plusDays(3L));
        this.priority.setValue(PriorityUI.NORMAL);

        setVisible(false);
    }
}
