package com.haulmont.testtask.ui;

import com.haulmont.testtask.dao.DoctorDAOImpl;
import com.haulmont.testtask.dao.PatientDAOImpl;
import com.haulmont.testtask.dao.RecipeDAOImpl;
import com.haulmont.testtask.models.*;
import com.haulmont.testtask.services.Services;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.*;

import java.time.LocalDate;

public class RecipeView extends VerticalLayout {

    Grid<Recipe> grid = new Grid<>(Recipe.class);

    //Create a TextField and ComboBox for class fields
    TextArea description = new TextArea("Description");
    ComboBox<Patient> patient = new ComboBox<>("Patient");
    ComboBox<Doctor> doctor = new ComboBox<>("Doctor");
    DateField dateCreation = new DateField("Date Creation");
    ComboBox<Priority> priority = new ComboBox<>("Priority");

    // Create a sub-window and set the content
    Window subWindow = new Window("Add dok");

    VerticalLayout subContent = new VerticalLayout();

    //Create a button to add a doctor to the database
    Button add = new Button("Add");

    //Create a button to delete a doctor from the database
    Button delete = new Button("Delete");

    //Create a button to update doctor information in the database
    Button update = new Button("Update");

    //Create a button to show statistics a doctor
    Button showStatistics = new Button("Show statistics");

    //Create a button to save the doctor in the database
    Button addDoc = new Button("Add");

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

        subWindow.setContent(subContent);
        subWindow.setModal(true);
        subWindow.center();


        Recipe recipe1 = new Recipe("hjekdnfvlmdfvml", patientServices.find((long) 1), doctorServices.find((long) 1), LocalDate.now(), Priority.CITO );
        Recipe recipe2 = new Recipe("dsfbv do ndv dflm dfdfgvgd ml", patientServices.find((long) 2), doctorServices.find((long) 1), LocalDate.now(), Priority.NORMAL );

        recipeServices.save(recipe1);
        recipeServices.save(recipe2);

        subContent.addComponent(description);
        subContent.addComponent(patient);
        patient.setItems(patientServices.findAll());
        patient.setItemCaptionGenerator(patient1 -> patient1.getId() + " - " + patient1.getName() + " " + patient1.getSurname() + " " + patient1.getPatronymic());
        subContent.addComponent(doctor);
        doctor.setItems(doctorServices.findAll());
        doctor.setItemCaptionGenerator(doctor -> doctor.getId() + " - " + doctor.getName() + " " + doctor.getSurname() + " " + doctor.getPatronymic());
        subContent.addComponent(dateCreation);
        priority.setItems(Priority.values());
        subContent.addComponent(priority);

        subContent.addComponent(addDoc);
        addDoc.addClickListener(clickEvent -> {
            Recipe newRecipe = new Recipe(description.getValue(), patient.getValue(), doctor.getValue(), dateCreation.getValue(), priority.getValue());
            recipeServices.save(newRecipe);
            dataProvider.getItems().add(newRecipe);
            dataProvider.refreshAll();
            description.setValue("");
            patient.setValue(null);
            doctor.setValue(null);
            dateCreation.setValue(null);
            subWindow.close();
        });


        add.addClickListener(clickEvent -> {
                    // Open it in the UI
                    getUI().addWindow(subWindow);
                }
        );

        delete.addClickListener(clickEvent ->
                Notification.show("You delete doctor 0_0"));

        update.addClickListener(clickEvent ->
                Notification.show("You update doctor -_-"));


    }
}
