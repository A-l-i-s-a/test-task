package com.haulmont.testtask.ui;

import com.haulmont.testtask.models.Doctor;
import com.haulmont.testtask.models.Patient;
import com.haulmont.testtask.models.Priority;
import com.haulmont.testtask.models.Recipe;
import com.haulmont.testtask.services.Services;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.*;

public class RecipeUpdateWindow extends Window {

    VerticalLayout layout = new VerticalLayout();

    //Create a TextField and ComboBox for class fields
    TextArea description = new TextArea("Description");
    ComboBox<Patient> patient = new ComboBox<>("Patient");
    ComboBox<Doctor> doctor = new ComboBox<>("Doctor");
    DateField dateCreation = new DateField("Date Creation");
    ComboBox<Priority> priority = new ComboBox<>("Priority");

    //Create a button to save the recipe in the database
    Button updateRecipe = new Button("Ok");

    HorizontalLayout horizontalLayout = new HorizontalLayout();

    public RecipeUpdateWindow(ListDataProvider<Recipe> dataProvider, Services<Recipe> recipeServices, Services<Doctor> doctorServices, Services<Patient> patientServices, Recipe recipe) {
        super("Update recipe");
        center();
        setModal(true);

        setContent(layout);

        layout.addComponent(description);
        layout.addComponent(patient);
        patient.setItems(patientServices.findAll());
        patient.setItemCaptionGenerator(patient1 -> patient1.getId() + " - " + patient1.getName() + " " + patient1.getSurname() + " " + patient1.getPatronymic());
        layout.addComponent(doctor);
        doctor.setItems(doctorServices.findAll());
        doctor.setItemCaptionGenerator(doctor -> doctor.getId() + " - " + doctor.getName() + " " + doctor.getSurname() + " " + doctor.getPatronymic());
        layout.addComponent(dateCreation);
        priority.setItems(Priority.values());
        layout.addComponent(priority);

        layout.addComponent(horizontalLayout);


        Binder<Recipe> binder = new Binder<>();

        binder.bind(description, Recipe::getDescription, Recipe::setDescription);
        binder.bind(patient, Recipe::getPatient, Recipe::setPatient);
        binder.bind(doctor, Recipe::getDoctor, Recipe::setDoctor);
        binder.bind(dateCreation, Recipe::getDateCreation, Recipe::setDateCreation);
        binder.bind(priority, Recipe::getPriority, Recipe::setPriority);

        binder.readBean(recipe);

        horizontalLayout.addComponent(updateRecipe);

        updateRecipe.addClickListener(clickEvent -> {
            try {

                binder.writeBean(recipe);
                recipeServices.update(recipe);
                dataProvider.refreshAll();

            } catch (ValidationException e) {
                Notification.show("Recipe could not be saved, " +
                        "please check error messages for each field.");
            }

            close();
        });

        horizontalLayout.addComponent(new Button("Cancel", event -> close()));

    }
}
