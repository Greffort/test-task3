package com.haulmont.testtask.ui.testAbstract;

import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

/*
 * Class defines the form for adding a doctor
 * @version 17.11.2020
 * Created by Greffort
 */

public abstract class AddView extends FormLayout {

    protected TextField id;
    protected TextField name;
    protected TextField lastName;
    protected TextField surname;

    protected Button save;
    protected Button cancel;

    public AddView() {
        setupDefaultLayout();
        addValidators();
        addListenerForButtons();
    }

    private void setupDefaultLayout() {
        save = new Button("Save");
        cancel = new Button("Cancel");
        save.setVisible(false);

        id = new TextField("ID");
        name = new TextField("Name");
        lastName = new TextField("Last name");
        surname = new TextField("Surname");

        setSizeUndefined();
        HorizontalLayout buttons = new HorizontalLayout(save, cancel);
        addComponents(id, name, lastName, surname, buttons);
        setupCustomLayout();
        addComponents(buttons);

        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

    }

    public abstract void setupCustomLayout();

    private void addListenerForButtons() {
        save.addClickListener(e -> save());
        cancel.addClickListener(e -> cancel());
    }

    public abstract void addValidators();

    private void cancel() {
        cleanDefaultForm();
        setVisible(false);
    }

    public abstract void save();

    public void cleanDefaultForm() {
        this.id.setValue("");
        this.name.setValue("");
        this.lastName.setValue("");
        this.surname.setValue("");
        setVisible(false);
        cleanCustomForm();
    }

    public abstract void cleanCustomForm();
}
