package com.haulmont.testtask.ui.testAbstract;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

import javax.annotation.PostConstruct;

public abstract class ObjectView extends VerticalLayout implements View {

    protected VerticalLayout rootLayout;
    protected HorizontalLayout bodyLayout;
    protected Button addButton;
    protected Button editButton;
    protected Button deleteButton;

    @PostConstruct
    void init() {
        setupDefaultLayout();
        addBody();
        updateList();
    }

    private void setupDefaultLayout() {
        setupCustomLayout();
        rootLayout = new VerticalLayout();
        bodyLayout = new HorizontalLayout();

        bodyLayout.setWidth("80%");
        bodyLayout.setWidthUndefined();
        bodyLayout.setSpacing(true);
        bodyLayout.setSpacing(false);
    }

    public abstract void setupCustomLayout();

    private void addBody() {
        addButton = new Button("Add");
        editButton = new Button("Edit");
        deleteButton = new Button("Delete");

        addButton.focus();
        addButtonsListeners();

        addCustomBody();
        rootLayout.addComponent(bodyLayout);
        addComponent(rootLayout);
    }

    public abstract void addCustomBody();

    public abstract void addButtonsListeners();

    public abstract void editObject();

    public abstract void deleteObject();

    public void closeSubWindows() {
        for (Window window : UI.getCurrent().getWindows()) {
            UI.getCurrent().removeWindow(window);
            window.close();
        }
    }

    public abstract void updateList();

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }

}
