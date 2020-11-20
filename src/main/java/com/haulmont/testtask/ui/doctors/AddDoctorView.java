package com.haulmont.testtask.ui.doctors;

import com.haulmont.testtask.controllers.Controller;
import com.haulmont.testtask.ui.CustomValidator;
import com.haulmont.testtask.ui.testAbstract.AddView;
import com.vaadin.data.ValidationResult;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.server.UserError;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;

import static com.haulmont.testtask.shared.LogMessages.Notification.SPECIFIED_ID_IS_BUSY;

/*
 * Class defines the form for adding a Patient
 * Created 12.11.2020
 * Changed 17.11.2020
 * Created by Greffort
 */

public class AddDoctorView extends AddView {

    private TextField specialization;
    private Boolean isCorrectlyID;
    private DoctorView view;

    public AddDoctorView(DoctorView view) {
//        super();
        this.view = view;
    }

    @Override
    public void setupCustomLayout() {
        isCorrectlyID = false;
        specialization = new TextField("specialization");
        addComponents(specialization);
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
        if (this.isCorrectlyID) {
            save.setVisible(true);
        }
    }

    @Override
    public void save() {
        DoctorModelUI doctorUIModel = new DoctorModelUI();

        doctorUIModel.setId(Long.valueOf(this.id.getValue()));
        doctorUIModel.setName(this.name.getValue());
        doctorUIModel.setLastName(this.lastName.getValue());
        doctorUIModel.setSurname(this.surname.getValue());
        doctorUIModel.setSpecialization(this.specialization.getValue());

        eventButton(doctorUIModel);
    }

    private void eventButton(DoctorModelUI doctorUIModel) {
        if (Controller.instance().checkIDForDoctor(doctorUIModel)) {
            Notification.show(SPECIFIED_ID_IS_BUSY);
        } else {
            Controller.instance().addDoctor(doctorUIModel);
            view.updateList();
            super.cleanDefaultForm();
        }
    }

    @Override
    public void cleanCustomForm() {
        this.isCorrectlyID = false;
        this.specialization.setValue("");
    }
}
