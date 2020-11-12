package com.haulmont.testtask.ui.recipes;

import com.haulmont.testtask.controllers.Controller;
import com.haulmont.testtask.ui.recipes.enumerators.PriorityUI;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.haulmont.testtask.shared.LogMessages.Notification.SELECT_ITEM;
import static com.haulmont.testtask.shared.LogMessages.Notification.SET_FILTER;

@SpringView(name = RecipeView.VIEW_NAME)
public class RecipeView extends VerticalLayout implements View {
    public static final String VIEW_NAME = "recipe";

    VerticalLayout rootLayout;
    HorizontalLayout bodyLayout;
    HorizontalLayout horizontalLayout;
    private TextField filterTextDescription = new TextField();
    private ComboBox<PriorityUI> filterTextPriority = new ComboBox<>();
    private TextField filterTextPatient = new TextField();
    private AddRecipeView form = new AddRecipeView(this);
    Grid<RecipeUIModel> grid = new Grid<>(RecipeUIModel.class);

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
        filterTextDescription.setPlaceholder("filter by description...");
        filterTextDescription.setValueChangeMode(ValueChangeMode.LAZY);

        filterTextPriority.setPlaceholder("filter by priority...");
        filterTextPriority.setItems(PriorityUI.NORMAL, PriorityUI.CITO, PriorityUI.STATIM);

        filterTextPatient.setPlaceholder("filter by patient...");
        filterTextPatient.setValueChangeMode(ValueChangeMode.LAZY);

        Button clearFilterTextBtn = new Button(FontAwesome.TIMES);
        Button applyFiltersButton = new Button("Apply");
        Button addButton = new Button("Add");
        Button editButton = new Button("Edit");
        Button deleteButton = new Button("Delete");

        addButton.focus();

        clearFilterTextBtn.setDescription("Clear the current filter");
        clearFilterTextBtn.addClickListener(e -> clearFiltersFields());
        addButton.addClickListener(click -> form.setVisible(true));
        applyFiltersButton.addClickListener(e -> applyFiltersForRecipes());
        editButton.addClickListener(click -> editRecipe());
        deleteButton.addClickListener(click -> deleteRecipe());

        CssLayout filtering = new CssLayout();
        filtering.addComponents(filterTextDescription, filterTextPriority,
                filterTextPatient, clearFilterTextBtn, applyFiltersButton);
        filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        rootLayout.addComponent(filtering);
        rootLayout.addComponent(horizontalLayout);
        bodyLayout.addComponents(addButton, editButton, deleteButton);
        rootLayout.addComponent(bodyLayout);
        addComponent(rootLayout);
    }

    private void clearFiltersFields() {
        filterTextDescription.clear();
        filterTextPriority.setValue(null);
        filterTextPatient.clear();
        updateList();
    }

    private void applyFiltersForRecipes() {
        if (filterTextDescription.getValue().equals("")
                && filterTextPatient.getValue().equals("")
                && (filterTextPriority.getValue() == null
                || String.valueOf(filterTextPriority.getValue()).equals(""))) {
            Notification.show(SET_FILTER);
        } else {
            String filterPriorityValue = "";
            if (filterTextPriority.getValue() != null) {
                filterPriorityValue = String.valueOf(filterTextPriority.getValue());
            }
            updateList(filterTextDescription.getValue(),
                    filterTextPatient.getValue(),
                    filterPriorityValue);
        }
    }

    private void editRecipe() {
        Optional<RecipeUIModel> recipeUI = grid.getSelectionModel().getFirstSelectedItem();
        if (recipeUI.isPresent()) {
            EditRecipeView view = new EditRecipeView(this);
            UI.getCurrent().addWindow(view.getSubWindows(recipeUI.get()));
        } else {
            Notification.show(SELECT_ITEM);
        }
    }

    private void deleteRecipe() {
        Optional<RecipeUIModel> recipeUI = grid.getSelectionModel().getFirstSelectedItem();
        if (recipeUI.isPresent()) {
            Controller.instance().deleteRecipeByID(recipeUI.get().getId());
            updateList();
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

    @Override
    public void enter(ViewChangeEvent event) {
    }

    public void updateList() {
        List<RecipeUIModel> customers = Controller.instance().findAllRecipes();
        if (customers != null) {
            grid.setItems(customers);
        }
    }

    public void updateList(String descriptionFilter,
                           String patientFilter,
                           String priorityFilter) {
        List<RecipeUIModel> customers = Controller.instance().findAllRecipes();
        List<RecipeUIModel> filteredRecipes = new ArrayList<>();

        if (customers != null) {
            for (RecipeUIModel recipeUIModel : customers) {
                String description = recipeUIModel.getDescription();
                String patient = String.valueOf(recipeUIModel.getPatient().getId());
                String priority = String.valueOf(recipeUIModel.getPriority());

                if (description.contains(descriptionFilter)
                        && patient.contains(patientFilter)
                        && priority.contains(priorityFilter)) {
                    filteredRecipes.add(recipeUIModel);
                }

            }
            grid.setItems(filteredRecipes);
        }
    }
}
