package com.haulmont.testtask.ui.patient;

import com.haulmont.testtask.controllers.Controller;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

import static com.haulmont.testtask.shared.LogMessages.Notification.PATIENT_HAS_RECIPES;
import static com.haulmont.testtask.shared.LogMessages.Notification.SELECT_ITEM;

@SpringView(name = PatientView.VIEW_NAME)
public class PatientView extends VerticalLayout implements View {
    public static final String VIEW_NAME = "patient";

    VerticalLayout rootLayout;
    HorizontalLayout bodyLayout;
    HorizontalLayout horizontalLayout;
    private AddPatientView form = new AddPatientView(this);
    Grid<PatientUIModel> grid = new Grid<>(PatientUIModel.class);

    @PostConstruct
    void init() {
        setupLayout();
        addBody();
        updateList();
    }

    private void setupLayout() {
        form.setVisible(false);
        rootLayout = new VerticalLayout();
        bodyLayout = new HorizontalLayout();

        horizontalLayout = new HorizontalLayout(grid, form);
        horizontalLayout.setSizeFull();
        grid.setSizeFull();
        horizontalLayout.setExpandRatio(grid, 1);

        bodyLayout.setWidth("100%");
        bodyLayout.setWidthUndefined();
        bodyLayout.setSpacing(false);
    }

    private void addBody() {

        Button addButton = new Button("Add");
        Button editButton = new Button("Edit");
        Button deleteButton = new Button("Delete");

        addButton.focus();

        addButton.addClickListener(click -> form.setVisible(true));
        editButton.addClickListener(click -> editDoctor());
        deleteButton.addClickListener(click -> deleteDoctor());

        rootLayout.addComponent(horizontalLayout);
        bodyLayout.addComponents(addButton, editButton, deleteButton);
        rootLayout.addComponent(bodyLayout);
        addComponent(rootLayout);
    }

    private void editDoctor() {
        Optional<PatientUIModel> patientUI = grid.getSelectionModel().getFirstSelectedItem();
        if (patientUI.isPresent()) {
            EditPatientView view = new EditPatientView(this);
            UI.getCurrent().addWindow(view.getSubWindows(patientUI.get()));
        } else {
            Notification.show(SELECT_ITEM);
        }
    }

    private void deleteDoctor() {
        Optional<PatientUIModel> patientUI = grid.getSelectionModel().getFirstSelectedItem();
        if (patientUI.isPresent()) {
            if (Controller.instance().findRecipeByPatientOrderById(patientUI.get())) {
                Notification.show(PATIENT_HAS_RECIPES);
            } else {
                Controller.instance().deletePatientByID(patientUI.get().getId());
                updateList();
            }
        } else {
            Notification.show(SELECT_ITEM);
        }
    }

    public void closeSubWindows() {
        for (Window window : UI.getCurrent().getWindows()) {
            UI.getCurrent().removeWindow(window);
            window.close();
        }
    }

    public void updateList() {
        List<PatientUIModel> customers = Controller.instance().findAllPatients();
        if (customers != null) {
            grid.setItems(customers);
        }
    }

    @Override
    public void enter(ViewChangeEvent event) {
    }
}
