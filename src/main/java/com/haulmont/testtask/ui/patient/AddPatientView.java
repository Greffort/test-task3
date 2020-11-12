package com.haulmont.testtask.ui.patient;

import com.haulmont.testtask.controllers.Controller;
import com.vaadin.data.ValidationResult;
import com.vaadin.data.Validator;
import com.vaadin.data.ValueContext;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.UserError;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import static com.haulmont.testtask.shared.LogMessages.Notification.SPECIFIED_ID_IS_BUSY;

public class AddPatientView extends FormLayout {

    private Boolean isCorrectlyID;
    private Boolean isCorrectlyPhoneNumber;

    private TextField id;
    private TextField name;
    private TextField lastName;
    private TextField surname;
    private TextField phoneNumber;

    private Button save;
    private Button cancel;

    private PatientView view;

    public AddPatientView(PatientView view) {
        this.view = view;
        setupLayout();
        addValidators();
        addListenerForButtons();
    }

    private void setupLayout() {
        save = new Button("Save");
        cancel = new Button("Cancel");
        isCorrectlyID = false;
        isCorrectlyPhoneNumber = false;
        save.setVisible(false);

        id = new TextField("ID");
        name = new TextField("Name");
        lastName = new TextField("Last name");
        surname = new TextField("Surname");
        phoneNumber = new TextField("phoneNumber");

        setSizeUndefined();
        HorizontalLayout buttons = new HorizontalLayout(save, cancel);
        addComponents(id, name, lastName, surname, phoneNumber, buttons);

        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(KeyCode.ENTER);
    }

    private void addListenerForButtons() {
        save.addClickListener(e -> this.save());
        cancel.addClickListener(e -> this.cancel());
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

        Validator validatorPhoneNumber = new RegexpValidator("Not valid", "^((\\+7|7|8)+([0-9]){10})$", true);
        phoneNumber.addValueChangeListener(event -> {
            ValidationResult result = validatorPhoneNumber.apply(event.getValue(), new ValueContext(id));

            if (result.isError()) {
                UserError error = new UserError(result.getErrorMessage());
                phoneNumber.setComponentError(error);
                save.setVisible(false);
            } else {
                phoneNumber.setComponentError(null);
                isCorrectlyPhoneNumber = true;
                checkCorrectlyTextFields();
            }
        });
    }

    private void checkCorrectlyTextFields() {
        if (this.isCorrectlyID && this.isCorrectlyPhoneNumber) {
            save.setVisible(true);
        }
    }

    private void cancel() {
        cleanForm();
        setVisible(false);
    }

    private void save() {
        PatientUIModel patientUIModel = new PatientUIModel();

        patientUIModel.setId(Long.valueOf(this.id.getValue()));
        patientUIModel.setName(this.name.getValue());
        patientUIModel.setLastName(this.lastName.getValue());
        patientUIModel.setSurname(this.surname.getValue());
        patientUIModel.setPhoneNumber(this.phoneNumber.getValue());

        if (Controller.instance().checkIDForPatient(patientUIModel)) {
            Notification.show(SPECIFIED_ID_IS_BUSY);
        } else {
            Controller.instance().addPatient(patientUIModel);
            view.updateList();
            cleanForm();
        }
    }

    private void cleanForm() {
        this.isCorrectlyID = false;
        this.isCorrectlyPhoneNumber = false;
        this.id.setValue("");
        this.name.setValue("");
        this.lastName.setValue("");
        this.surname.setValue("");
        this.phoneNumber.setValue("");
        setVisible(false);
    }
}
