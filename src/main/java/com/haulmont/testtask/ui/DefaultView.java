package com.haulmont.testtask.ui;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import javax.annotation.PostConstruct;

@SpringView(name = DefaultView.VIEW_NAME)
public class DefaultView extends VerticalLayout implements View {
    public static final String VIEW_NAME = "";

    VerticalLayout layout = new VerticalLayout();

    @PostConstruct
    void init() {
        Label label1 = new Label("Welcome!");
        label1.addStyleName(ValoTheme.LABEL_H1);

        Label label2 = new Label("Select a page and get started");
        label2.addStyleName(ValoTheme.LABEL_H2);

        layout.addComponents(label1, label2);
        layout.setComponentAlignment(label1, Alignment.TOP_CENTER);
        layout.setComponentAlignment(label2, Alignment.TOP_CENTER);
        addComponent(layout);
    }

    @Override
    public void enter(ViewChangeEvent event) {
    }
}
