package com.haulmont.testtask.ui;

import com.haulmont.testtask.dao.DoctorDAOImpl;
import com.haulmont.testtask.dao.PatientDAOImpl;
import com.haulmont.testtask.dao.RecipeDAOImpl;
import com.haulmont.testtask.models.*;
import com.haulmont.testtask.services.Services;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.*;
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
        grid.setColumnOrder("id", "description", "patient", "doctor", "dateCreation");

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
