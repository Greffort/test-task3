package com.haulmont.testtask.ui.testAbstract;

import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public abstract class EditView<T> extends Window {

    protected VerticalLayout subContent = new VerticalLayout();

    protected Boolean isCorrectlyID;

    protected TextField id;
    protected TextField name;
    protected TextField lastName;
    protected TextField surname;

    protected Button save;
    protected Button cancel;

    protected EditView() {
        setupDefaultLayout();
        addValidators();
        addListenerForButtons();
    }

    private void setupDefaultLayout() {
        save = new Button("Save");
        cancel = new Button("Cancel");
        isCorrectlyID = false;
        save.setVisible(false);

        id = new TextField("ID");
        name = new TextField("Name");
        lastName = new TextField("Last name");
        surname = new TextField("Surname");

        setSizeUndefined();
        HorizontalLayout buttons = new HorizontalLayout(save, cancel);
        subContent.addComponents(id, name, lastName, surname);
        setupCustomLayout();
        subContent.addComponents(buttons);

        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
    }

    public abstract void setupCustomLayout();

    private void addListenerForButtons() {
        save.addClickListener(e -> save());
        cancel.addClickListener(e -> cancel());
    }

    public Window getSubWindows(T object) {
        Window subWindow = new Window("Sub-window");
        subWindow.setContent(subContent);

        setObject(object);

        subWindow.center();
        return subWindow;
    }

    public abstract void setObject(T object);

    public abstract void addValidators();

    public abstract void save();

    public abstract void eventButton(T ob);

    private void cancel() {
        cleanDefaultForm();
    }

    public void cleanDefaultForm() {
        this.isCorrectlyID = false;
        this.id.setValue("");
        this.name.setValue("");
        this.lastName.setValue("");
        this.surname.setValue("");
        cleanCustomForm();
        this.close();
    }

    public abstract void cleanCustomForm();
}
