package com.haulmont.testtask.ui.doctors;

import com.haulmont.testtask.controllers.Controller;
import com.haulmont.testtask.ui.recipes.RecipeUIModel;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.List;

public class StatisticView extends Window {
    private VerticalLayout subContent;
    private Button cancel;
    private DoctorView view;

    public StatisticView(DoctorView view) {
        subContent = new VerticalLayout();
        this.view = view;
        cancel = new Button("Cancel");

        subContent.setStyleName(ValoTheme.PANEL_SCROLL_INDICATOR);

        List<DoctorUIModel> doctorUIModels = getDoctors();
        getStatistic(doctorUIModels);

        setSizeUndefined();
        HorizontalLayout buttons = new HorizontalLayout(cancel);
        subContent.addComponents(buttons);

        cancel.addClickListener(e -> cancel());
    }

    private void getStatistic(List<DoctorUIModel> doctorUIModels) {
        if (doctorUIModels != null) {
            for (DoctorUIModel doctorUIModel : doctorUIModels) {
                Label label1 = new Label("Doctor: " + doctorUIModel.getName() + " " + doctorUIModel.getSurname());
                Label label2 = new Label("Specialization: " + doctorUIModel.getSpecialization());
                Label label3 = new Label("Total recipes: " + getRecipesByDoctorID(doctorUIModel).size());
                Label label4 = new Label("");
                subContent.addComponents(label1, label2, label3, label4);
            }
        }
    }

    private List<DoctorUIModel> getDoctors() {
        return Controller.instance().findAllDoctors();
    }

    private List<RecipeUIModel> getRecipesByDoctorID(DoctorUIModel doctorUIModel) {
        return Controller.instance().findRecipeByDoctorId(doctorUIModel);
    }

    public Window getSubWindows() {
        Window subWindow = new Window("Sub-window");
        subWindow.setContent(subContent);

        subWindow.center();
        return subWindow;
    }

    private void cancel() {
        cleanForm();
    }

    private void cleanForm() {
        this.view.closeSubWindows();
        this.close();
    }
}
