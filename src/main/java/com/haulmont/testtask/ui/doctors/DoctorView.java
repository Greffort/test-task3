package com.haulmont.testtask.ui.doctors;

import com.haulmont.testtask.controllers.Controller;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

import static com.haulmont.testtask.shared.LogMessages.Notification.DOCTOR_HAS_RECIPES;
import static com.haulmont.testtask.shared.LogMessages.Notification.SELECT_ITEM;

@SpringView(name = DoctorView.VIEW_NAME)
public class DoctorView extends VerticalLayout implements View {
    public static final String VIEW_NAME = "doctor";

    VerticalLayout rootLayout;
    HorizontalLayout bodyLayout;
    HorizontalLayout horizontalLayout;
    private AddDoctorView form = new AddDoctorView(this);
    Grid<DoctorUIModel> grid = new Grid<>(DoctorUIModel.class);

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

        bodyLayout.setWidth("80%");
        bodyLayout.setWidthUndefined();
        bodyLayout.setSpacing(true);
        bodyLayout.setSpacing(false);
    }

    private void addBody() {
        Button addButton = new Button("Add");
        Button editButton = new Button("Edit");
        Button statisticButton = new Button("Statistic");
        Button deleteButton = new Button("Delete");

        addButton.focus();

        addButton.addClickListener(click -> form.setVisible(true));
        editButton.addClickListener(click -> editDoctor());
        statisticButton.addClickListener(click -> getStatistic());
        deleteButton.addClickListener(click -> deleteDoctor());

        rootLayout.addComponent(horizontalLayout);
        bodyLayout.addComponents(addButton, editButton, statisticButton, deleteButton);
        rootLayout.addComponent(bodyLayout);
        addComponent(rootLayout);
    }

    private void getStatistic() {
        StatisticView view = new StatisticView(this);
        Window window = view.getSubWindows();
        window.setSizeFull();
        window.getScrollLeft();
        UI.getCurrent().addWindow(window);
    }

    private void editDoctor() {
        Optional<DoctorUIModel> doctorUI = grid.getSelectionModel().getFirstSelectedItem();
        if (doctorUI.isPresent()) {
            EditDoctorView view = new EditDoctorView(this);
            UI.getCurrent().addWindow(view.getSubWindows(doctorUI.get()));
        } else {
            Notification.show(SELECT_ITEM);
        }
    }

    private void deleteDoctor() {
        Optional<DoctorUIModel> doctorUI = grid.getSelectionModel().getFirstSelectedItem();
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

    public void closeSubWindows() {
        for (Window window : UI.getCurrent().getWindows()) {
            UI.getCurrent().removeWindow(window);
            window.close();
        }
    }

    public void updateList() {
        List<DoctorUIModel> customers = Controller.instance().findAllDoctors();
        if (customers != null) {
            grid.setItems(customers);
        }
    }

    @Override
    public void enter(ViewChangeEvent event) {
    }
}
