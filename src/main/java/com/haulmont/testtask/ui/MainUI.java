package com.haulmont.testtask.ui;

import com.haulmont.testtask.controllers.Controller;
import com.haulmont.testtask.ui.doctors.DoctorView;
import com.haulmont.testtask.ui.patient.PatientView;
import com.haulmont.testtask.ui.recipes.RecipeView;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/*
 * The class defines the main ui of the application
 * @version 12.11.2020
 * Created by Greffort
 */

@Theme(ValoTheme.THEME_NAME)
@SpringUI
@SpringViewDisplay
public class MainUI extends UI implements ViewDisplay {

    @Autowired
    private ApplicationContext context;

    private VerticalLayout layout;
    private VerticalLayout bodyLayout;

    @Override
    protected void init(VaadinRequest request) {
        initializeApplicationContext();
        setupLayout();
        addHeader();
        addNavigationBar();
        addForm();
    }

    private void initializeApplicationContext() {
        Controller.instance().init(context);
    }

    private void setupLayout() {
        layout = new VerticalLayout();
        layout.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        setContent(layout);
        bodyLayout = new VerticalLayout();
        bodyLayout.setDefaultComponentAlignment(Alignment.TOP_CENTER);
    }

    private void addNavigationBar() {
        final CssLayout navigationBar = new CssLayout();
        navigationBar.addStyleName(ValoTheme.THEME_NAME);
        navigationBar.addComponent(createNavigationButton("Home",
                DefaultView.VIEW_NAME));
        navigationBar.addComponent(createNavigationButton("Doctors",
                DoctorView.VIEW_NAME));
        navigationBar.addComponent(createNavigationButton("Patients",
                PatientView.VIEW_NAME));
        navigationBar.addComponent(createNavigationButton("Recipes",
                RecipeView.VIEW_NAME));
        layout.addComponent(navigationBar);
        layout.setComponentAlignment(navigationBar, Alignment.TOP_CENTER);
    }

    private void addHeader() {
        Label header = new Label("TEST TASK 3");
        header.addStyleName(ValoTheme.LABEL_H3);
        layout.addComponent(header);
        layout.setComponentAlignment(header, Alignment.TOP_CENTER);
    }

    private void addForm() {
        layout.addComponent(bodyLayout);
        layout.setComponentAlignment(bodyLayout, Alignment.TOP_CENTER);
    }


    @NotNull
    private Button createNavigationButton(String caption, final String viewName) {
        Button button = new Button(caption);
        button.addStyleName(ValoTheme.THEME_NAME);
        button.addClickListener(
                event -> getUI().getNavigator().navigateTo(viewName));
        return button;
    }

    @Override
    public void showView(View view) {
        bodyLayout.removeAllComponents();
        bodyLayout.addComponent((Component) view);
        bodyLayout.setComponentAlignment((Component) view, Alignment.TOP_CENTER);
        bodyLayout.setDefaultComponentAlignment(Alignment.TOP_CENTER);
    }
}
