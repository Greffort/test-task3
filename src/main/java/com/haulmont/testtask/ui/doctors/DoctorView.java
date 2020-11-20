package com.haulmont.testtask.ui.doctors;

import com.haulmont.testtask.controllers.Controller;
import com.haulmont.testtask.ui.Helper;
import com.haulmont.testtask.ui.testAbstract.ObjectView;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;

import java.util.List;
import java.util.Optional;

import static com.haulmont.testtask.shared.LogMessages.Notification.DOCTOR_HAS_RECIPES;
import static com.haulmont.testtask.shared.LogMessages.Notification.SELECT_ITEM;

/*
 * The class defines the main page for managing doctor objects
 * Created 12.11.2020
 * Changed 17.11.2020
 * Created by Greffort
 */

@SpringView(name = Helper.DOCTOR_VIEW_NAME)
public class DoctorView extends ObjectView {

    private HorizontalLayout horizontalLayout;
    private Grid<DoctorModelUI> grid;
    private Button statisticButton;
    private AddDoctorView form;

    @Override
    public void setupCustomLayout() {
        form = new AddDoctorView(this);
        form.setVisible(false);

        grid = new Grid<>(DoctorModelUI.class);
        grid.setSizeFull();

        horizontalLayout = new HorizontalLayout(grid, form);
        horizontalLayout.setExpandRatio(grid, 1);
        horizontalLayout.setSizeFull();

        statisticButton = new Button("Statistic");
    }

    @Override
    public void addCustomBody() {
        rootLayout.addComponent(horizontalLayout);
        bodyLayout.addComponents(addButton, editButton,
                statisticButton, deleteButton);
    }

    @Override
    public void addButtonsListeners() {
        addButton.addClickListener(click -> form.setVisible(true));
        editButton.addClickListener(click -> editObject());
        statisticButton.addClickListener(click -> getStatistic());
        deleteButton.addClickListener(click -> deleteObject());
    }

    @Override
    public void editObject() {
        Optional<DoctorModelUI> doctorUI = grid.getSelectionModel()
                .getFirstSelectedItem();
        if (doctorUI.isPresent()) {
            EditDoctorView view = new EditDoctorView(this);
            UI.getCurrent().addWindow(view.getSubWindows(doctorUI.get()));
        } else {
            Notification.show(SELECT_ITEM);
        }
    }

    @Override
    public void deleteObject() {
        Optional<DoctorModelUI> doctorUI = grid.getSelectionModel()
                .getFirstSelectedItem();
        if (doctorUI.isPresent()) {
            if (Controller.instance().findRecipeByDoctorOrderById(doctorUI.get())) {
                Notification.show(DOCTOR_HAS_RECIPES);
            } else {
                Controller.instance().deleteDoctorByID(doctorUI.get().getId());
                updateList();
            }
        } else {
            Notification.show(SELECT_ITEM);
        }
    }

    private void getStatistic() {
        StatisticView view = new StatisticView(this);
        Window window = view.getSubWindows();
        window.setSizeFull();
        window.getScrollLeft();
        UI.getCurrent().addWindow(window);
    }

    public void updateList() {
        List<DoctorModelUI> customers = Controller.instance().findAllDoctors();
        if (customers != null) {
            grid.setItems(customers);
        }
    }
}
