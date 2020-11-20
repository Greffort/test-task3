package com.haulmont.testtask.ui.doctors;

import com.haulmont.testtask.controllers.Controller;
import com.haulmont.testtask.ui.CustomValidator;
import com.haulmont.testtask.ui.testAbstract.EditView;
import com.vaadin.data.ValidationResult;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.server.UserError;
import com.vaadin.ui.TextField;
import org.jetbrains.annotations.NotNull;

/*
 * The class defines a sub-window for editing the Doctor object
 * @version 12.11.2020
 * Created by Greffort
 */

public class EditDoctorView extends EditView<DoctorModelUI> {

    private TextField specialization;
    private DoctorView view;

    public EditDoctorView(DoctorView view) {
        super();
        this.view = view;
    }

    @Override
    public void setupCustomLayout() {
        specialization = new TextField("specialization");
        subContent.addComponents(specialization);
    }

    public void setObject(@NotNull DoctorModelUI doctorUIModel){
        id.setValue(String.valueOf(doctorUIModel.getId()));
        id.setEnabled(false);
        name.setValue(doctorUIModel.getName());
        lastName.setValue(doctorUIModel.getLastName());
        surname.setValue(doctorUIModel.getSurname());
        specialization.setValue(doctorUIModel.getSpecialization());

    }

    public void addValidators() {
        ValidationResult result = CustomValidator.addValidator(id, new RegexpValidator("Not valid",
                "^[0-9]+$",
                true));
        if (result.isError()) {
            UserError error = new UserError(result.getErrorMessage());
            id.setComponentError(error);
            save.setVisible(false);
        } else {
            id.setComponentError(null);
            isCorrectlyID = true;
            checkCorrectlyTextFields();
        }
    }

    private void checkCorrectlyTextFields() {
        if (isCorrectlyID) {
            save.setVisible(true);
        }
    }

    public void save() {
        DoctorModelUI doctorUIModel = new DoctorModelUI();

        doctorUIModel.setId(Long.valueOf(id.getValue()));
        doctorUIModel.setName(name.getValue());
        doctorUIModel.setLastName(lastName.getValue());
        doctorUIModel.setSurname(surname.getValue());
        doctorUIModel.setSpecialization(specialization.getValue());

        eventButton(doctorUIModel);
    }

    public void eventButton(DoctorModelUI doctorUIModel) {
        Controller.instance().editDoctor(doctorUIModel);
        view.updateList();
        super.cleanDefaultForm();
    }

    @Override
    public void cleanCustomForm() {
        this.specialization.setValue("");
        this.view.closeSubWindows();
    }
}
