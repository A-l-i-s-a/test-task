package com.haulmont.testtask.ui;

import com.haulmont.testtask.dao.DoctorDAOImpl;
import com.haulmont.testtask.dao.PatientDAOImpl;
import com.haulmont.testtask.dao.RecipeDAOImpl;
import com.haulmont.testtask.models.*;
import com.haulmont.testtask.services.Services;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.HeaderRow;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.exception.ConstraintViolationException;

import java.time.LocalDate;

public class RecipeView extends VerticalLayout {

    Grid<Recipe> grid = new Grid<>(Recipe.class);

    VerticalLayout subContent = new VerticalLayout();

    //Create a button to add a recipe to the database
    Button add = new Button("Add");

    //Create a button to delete a recipe from the database
    Button delete = new Button("Delete");

    //Create a button to update recipe information in the database
    Button update = new Button("Update");

    HorizontalLayout horizontalLayout = new HorizontalLayout();

    public RecipeView() {

        Services<Recipe> recipeServices = new Services<>(new RecipeDAOImpl());
        Services<Patient> patientServices = new Services<>(new PatientDAOImpl());
        Services<Doctor> doctorServices = new Services<>(new DoctorDAOImpl());
        ListDataProvider<Recipe> dataProvider = new ListDataProvider<>(recipeServices.findAll());

        addComponent(grid);

        horizontalLayout.addComponent(add);
        horizontalLayout.addComponent(update);
        horizontalLayout.addComponent(delete);

        addComponent(horizontalLayout);

        grid.setDataProvider(dataProvider);
        grid.setSizeFull();
        grid.setColumnOrder("id", "description", "patient", "doctor", "dateCreation", "validity");

        HeaderRow filterRow = grid.appendHeaderRow();
        // Description filter
        TextField descriptionField = new TextField();
        descriptionField.addValueChangeListener(event -> dataProvider.addFilter(
                recipe -> StringUtils.containsIgnoreCase(recipe.getDescription(),
                        descriptionField.getValue())));

        descriptionField.setValueChangeMode(ValueChangeMode.EAGER);

        filterRow.getCell("description").setComponent(descriptionField);
        descriptionField.setSizeFull();
        descriptionField.setPlaceholder("Filter");

        // Patient filter
        TextField patientField = new TextField();
        patientField.addValueChangeListener(event -> dataProvider.addFilter(
                recipe -> StringUtils.containsIgnoreCase(recipe.getPatient().toString(),
                        patientField.getValue())));

        patientField.setValueChangeMode(ValueChangeMode.EAGER);

        filterRow.getCell("patient").setComponent(patientField);
        patientField.setSizeFull();
        patientField.setPlaceholder("Filter");

        // Priority filter
        TextField priorityField = new TextField();
        priorityField.addValueChangeListener(event -> dataProvider.addFilter(
                recipe -> StringUtils.containsIgnoreCase(recipe.getPriority().toString(),
                        priorityField.getValue())));

        priorityField.setValueChangeMode(ValueChangeMode.EAGER);

        filterRow.getCell("priority").setComponent(priorityField);
        priorityField.setSizeFull();
        priorityField.setPlaceholder("Filter");

        add.addClickListener(clickEvent -> {
                    // Open it in the UI
                    getUI().addWindow(new RecipeAddWindow(dataProvider, recipeServices, doctorServices, patientServices));
                }
        );

        delete.addClickListener(clickEvent -> {
            Recipe recipe = null;
            for (Recipe recipeFromSet : grid.getSelectedItems()) {
                recipe = recipeFromSet;
            }

            if (recipe != null) {
                try {
                    recipeServices.delete(recipe);
                    dataProvider.refreshAll();
                } catch (Exception exception) {
                    Notification.show("Could not execute statement. " + exception);
                }
            }
        });

        update.addClickListener(clickEvent -> {
            Recipe recipe = null;
            for (Recipe recipeFromSet : grid.getSelectedItems()) {
                recipe = recipeFromSet;
            }

            if (recipe != null) {
                getUI().addWindow(new RecipeUpdateWindow(dataProvider, recipeServices, doctorServices, patientServices, recipe));
            } else {
                Notification.show("Select line");
            }
                });


    }
}
