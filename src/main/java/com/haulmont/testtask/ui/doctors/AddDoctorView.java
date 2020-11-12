package com.haulmont.testtask.ui.doctors;

import com.haulmont.testtask.controllers.Controller;
import com.vaadin.data.ValidationResult;
import com.vaadin.data.Validator;
import com.vaadin.data.ValueContext;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.UserError;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import static com.haulmont.testtask.shared.LogMessages.Notification.SPECIFIED_ID_IS_BUSY;

/*
 * Class defines the form for adding a Patient
 * @version 12.11.2020
 * Created by Aleksandr Kravchina
 */

public class AddDoctorView extends FormLayout {

    private Boolean isCorrectlyID;

    private TextField id;
    private TextField name;
    private TextField lastName;
    private TextField surname;
    private TextField specialization;

    private Button save;
    private Button cancel;

    private DoctorView view;

    public AddDoctorView(DoctorView view) {
        this.view = view;
        setupLayout();
        addValidators();
        addListenerForButtons();
    }

    private void setupLayout() {
        save = new Button("Save");
        cancel = new Button("Cancel");
        isCorrectlyID = false;
        save.setVisible(false);

        id = new TextField("ID");
        name = new TextField("Name");
        lastName = new TextField("Last name");
        surname = new TextField("Surname");
        specialization = new TextField("specialization");

        setSizeUndefined();
        HorizontalLayout buttons = new HorizontalLayout(save, cancel);
        addComponents(id, name, lastName, surname, specialization, buttons);

        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
    }

    private void addListenerForButtons() {
        save.addClickListener(e -> save());
        cancel.addClickListener(e -> cancel());
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
    }

    private void checkCorrectlyTextFields() {
        if (this.isCorrectlyID) {
            save.setVisible(true);
        }
    }

    private void cancel() {
        cleanForm();
        setVisible(false);
    }

    private void save() {
        DoctorUIModel doctorUIModel = new DoctorUIModel();

        doctorUIModel.setId(Long.valueOf(this.id.getValue()));
        doctorUIModel.setName(this.name.getValue());
        doctorUIModel.setLastName(this.lastName.getValue());
        doctorUIModel.setSurname(this.surname.getValue());
        doctorUIModel.setSpecialization(this.specialization.getValue());

        eventButton(doctorUIModel);
    }

    private void eventButton(DoctorUIModel doctorUIModel) {
        if (Controller.instance().checkIDForDoctor(doctorUIModel)) {
            Notification.show(SPECIFIED_ID_IS_BUSY);
        } else {
            Controller.instance().addDoctor(doctorUIModel);
            view.updateList();
            cleanForm();
        }
    }

    private void cleanForm() {
        this.isCorrectlyID = false;
        this.id.setValue("");
        this.name.setValue("");
        this.lastName.setValue("");
        this.surname.setValue("");
        this.specialization.setValue("");
        setVisible(false);
    }
}
