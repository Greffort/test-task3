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
import org.jetbrains.annotations.NotNull;


public class EditDoctorView extends Window {
    private VerticalLayout subContent = new VerticalLayout();

    private Boolean isCorrectlyID;

    private TextField id;
    private TextField name;
    private TextField lastName;
    private TextField surname;
    private TextField specialization;

    private Button save;
    private Button cancel;

    private DoctorView view;

    public EditDoctorView(DoctorView view) {
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
        subContent.addComponents(id, name, lastName, surname, specialization, buttons);

        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
    }

    private void addListenerForButtons() {
        save.addClickListener(e -> save());
        cancel.addClickListener(e -> cancel());
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
    }

    private void checkCorrectlyTextFields() {
        if (isCorrectlyID) {
            save.setVisible(true);
        }
    }

    private void cancel() {
        cleanForm();
    }

    private void save() {
        DoctorUIModel doctorUIModel = new DoctorUIModel();

        doctorUIModel.setId(Long.valueOf(id.getValue()));
        doctorUIModel.setName(name.getValue());
        doctorUIModel.setLastName(lastName.getValue());
        doctorUIModel.setSurname(surname.getValue());
        doctorUIModel.setSpecialization(specialization.getValue());

        eventButton(doctorUIModel);
    }

    private void eventButton(DoctorUIModel doctorUIModel) {
        Controller.instance().editDoctor(doctorUIModel);
        view.updateList();
        cleanForm();
    }

    private void cleanForm() {
        this.isCorrectlyID = false;
        this.id.setValue("");
        this.name.setValue("");
        this.lastName.setValue("");
        this.surname.setValue("");
        this.specialization.setValue("");
        this.view.closeSubWindows();
        this.close();
    }

    public Window getSubWindows(@NotNull DoctorUIModel doctorUIModel) {
        Window subWindow = new Window("Sub-window");
        subWindow.setContent(subContent);

        id.setValue(String.valueOf(doctorUIModel.getId()));
        id.setEnabled(false);
        name.setValue(doctorUIModel.getName());
        lastName.setValue(doctorUIModel.getLastName());
        surname.setValue(doctorUIModel.getSurname());
        specialization.setValue(doctorUIModel.getSpecialization());

        subWindow.center();
        return subWindow;
    }
}
