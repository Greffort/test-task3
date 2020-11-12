package com.haulmont.testtask.ui.patient;

import com.haulmont.testtask.controllers.Controller;
import com.vaadin.data.ValidationResult;
import com.vaadin.data.Validator;
import com.vaadin.data.ValueContext;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.UserError;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;


public class EditPatientView extends Window {
    private VerticalLayout subContent = new VerticalLayout();

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

    public EditPatientView(PatientView view) {
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
        subContent.addComponents(id, name, lastName, surname, phoneNumber, buttons);

        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
    }

    private void addListenerForButtons() {
        save.addClickListener(e -> save(view));
        cancel.addClickListener(e -> cancel());
    }


    public Window getSubWindows(PatientUIModel patientUIModel) {
        Window subWindow = new Window("Sub-window");
        subWindow.setContent(subContent);

        id.setValue(String.valueOf(patientUIModel.getId()));
        id.setEnabled(false);
        name.setValue(patientUIModel.getName());
        lastName.setValue(patientUIModel.getLastName());
        surname.setValue(patientUIModel.getSurname());
        phoneNumber.setValue(patientUIModel.getPhoneNumber());

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
        if (isCorrectlyID && isCorrectlyPhoneNumber) {
            save.setVisible(true);
        }
    }

    private void cancel() {
        cleanForm();
    }

    private void save(PatientView view) {
        PatientUIModel patientUIModel = new PatientUIModel();

        patientUIModel.setId(Long.valueOf(id.getValue()));
        patientUIModel.setName(name.getValue());
        patientUIModel.setLastName(lastName.getValue());
        patientUIModel.setSurname(surname.getValue());
        patientUIModel.setPhoneNumber(phoneNumber.getValue());

        Controller.instance().editPatient(patientUIModel);
        view.updateList();
        cleanForm();
    }

    private void cleanForm() {
        this.isCorrectlyID = false;
        this.isCorrectlyPhoneNumber = false;
        this.id.setValue("");
        this.name.setValue("");
        this.lastName.setValue("");
        this.surname.setValue("");
        this.phoneNumber.setValue("");
        this.view.closeSubWindows();
        this.close();
    }
}
