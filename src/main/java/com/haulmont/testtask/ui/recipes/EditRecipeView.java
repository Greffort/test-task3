package com.haulmont.testtask.ui.recipes;

import com.haulmont.testtask.controllers.Controller;
import com.haulmont.testtask.ui.doctors.DoctorUIModel;
import com.haulmont.testtask.ui.patient.PatientUIModel;
import com.haulmont.testtask.ui.recipes.enumerators.PriorityUI;
import com.vaadin.data.ValidationResult;
import com.vaadin.data.Validator;
import com.vaadin.data.ValueContext;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.UserError;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.time.LocalDate;


public class EditRecipeView extends Window {
    private VerticalLayout subContent = new VerticalLayout();

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

    public EditRecipeView(RecipeView view) {
        this.view = view;
        setupLayout();
        addValidators();
        addListenerForButtons();
    }

    private void setupLayout() {
        this.save = new Button("Save");
        this.cancel = new Button("Cancel");
        this.isCorrectlyID = false;
        this.isCorrectlyPatientID = false;
        this.isCorrectlyDoctorID = false;
        this.save.setVisible(false);

        id = new TextField("ID");
        description = new TextField("Description");
        patient = new TextField("Patient ID");
        doctor = new TextField("Doctor ID");
        createDate = new DateField("Createdate");
        validity = new DateField("Validity");
        priority = new ComboBox<>("Priority");

        createDate.setValue(LocalDate.now());
        validity.setValue(LocalDate.now().plusDays(3L));

        priority.setItems(PriorityUI.NORMAL, PriorityUI.CITO, PriorityUI.STATIM);
        priority.setValue(PriorityUI.NORMAL);

        setSizeUndefined();
        HorizontalLayout buttons = new HorizontalLayout(save, cancel);
        subContent.addComponents(id, description, patient, doctor, createDate, validity, priority, buttons);

        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
    }

    private void addListenerForButtons() {
        save.addClickListener(e -> save(view));
        cancel.addClickListener(e -> cancel());
    }

    public Window getSubWindows(RecipeUIModel recipeUIModel) {
        Window subWindow = new Window("Sub-window");
        subWindow.setContent(subContent);

        id.setValue(String.valueOf(recipeUIModel.getId()));
        id.setEnabled(false);

        description.setValue(recipeUIModel.getDescription());
        patient.setValue(String.valueOf(recipeUIModel.getPatient().getId()));
        doctor.setValue(String.valueOf(recipeUIModel.getDoctor().getId()));
        createDate.setValue(LocalDate.parse(recipeUIModel.getCreateDate()));
        validity.setValue(LocalDate.parse(recipeUIModel.getValidity()));
        priority.setValue(recipeUIModel.getPriority());

        subWindow.center();
        return subWindow;
    }

    private void addValidators() {
        Validator validatorID = new RegexpValidator("Not valid", "^[0-9]+$", true);
        id.addValueChangeListener(event -> {
            ValidationResult result = validatorID.apply(event.getValue(), new ValueContext(id));

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

        Validator validatorDoctorID = new RegexpValidator("Not valid", "^[0-9]+$", true);
        id.addValueChangeListener(event -> {
            ValidationResult result = validatorDoctorID.apply(event.getValue(), new ValueContext(id));

            if (result.isError()) {
                UserError error = new UserError(result.getErrorMessage());
                id.setComponentError(error);
                save.setVisible(false);
            } else {
                id.setComponentError(null);
                isCorrectlyDoctorID = true;
                checkCorrectlyTextFields();
            }
        });

        Validator validatorPatientID = new RegexpValidator("Not valid", "^[0-9]+$", true);
        id.addValueChangeListener(event -> {
            ValidationResult result = validatorPatientID.apply(event.getValue(), new ValueContext(id));

            if (result.isError()) {
                UserError error = new UserError(result.getErrorMessage());
                id.setComponentError(error);
                save.setVisible(false);
            } else {
                id.setComponentError(null);
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
    }

    private void save(RecipeView view) {
        RecipeUIModel recipeUIModel = new RecipeUIModel();

        recipeUIModel.setId(Long.valueOf(id.getValue()));
        recipeUIModel.setDescription(description.getValue());
        recipeUIModel.setPatient(getPatientByID(Long.valueOf(patient.getValue())));
        recipeUIModel.setDoctor(getDoctorByID(Long.valueOf(doctor.getValue())));
        recipeUIModel.setCreateDate(String.valueOf(createDate.getValue()));
        recipeUIModel.setValidity(String.valueOf(validity.getValue()));
        recipeUIModel.setPriority(priority.getValue());

        Controller.instance().editRecipe(recipeUIModel);
        view.updateList();
        cleanForm();
    }

    private DoctorUIModel getDoctorByID(Long id) {
        return Controller.instance().findDoctorByID(id);
    }

    private PatientUIModel getPatientByID(Long id) {
        return Controller.instance().findPatientByID(id);
    }


    private void cleanForm() {
        this.isCorrectlyID = false;
        this.isCorrectlyDoctorID = false;
        this.isCorrectlyPatientID = false;

        this.id.setValue("");
        this.description.setValue("");
        this.patient.setValue("");
        this.doctor.setValue("");
        this.createDate.setValue(LocalDate.now());
        this.validity.setValue(LocalDate.now().plusDays(3L));
        this.priority.setValue(PriorityUI.NORMAL);

        this.view.closeSubWindows();
        this.close();

        setVisible(false);
    }
}
