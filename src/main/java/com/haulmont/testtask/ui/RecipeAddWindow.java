package com.haulmont.testtask.ui;

import com.haulmont.testtask.models.Doctor;
import com.haulmont.testtask.models.Patient;
import com.haulmont.testtask.models.Priority;
import com.haulmont.testtask.models.Recipe;
import com.haulmont.testtask.services.Services;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.*;

public class RecipeAddWindow extends Window {
    VerticalLayout layout = new VerticalLayout();

    //Create a TextField and ComboBox for class fields
    TextArea description = new TextArea("Description");
    ComboBox<Patient> patient = new ComboBox<>("Patient");
    ComboBox<Doctor> doctor = new ComboBox<>("Doctor");
    DateField dateCreation = new DateField("Date Creation");
    ComboBox<Priority> priority = new ComboBox<>("Priority");

    //Create a button to save the recipe in the database
    Button addRecipe = new Button("Ok");

    HorizontalLayout horizontalLayout = new HorizontalLayout();

    public RecipeAddWindow(ListDataProvider<Recipe> dataProvider, Services<Recipe> recipeServices, Services<Doctor> doctorServices, Services<Patient> patientServices) {
        super("Add recipe");
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
        horizontalLayout.addComponent(addRecipe);
        addRecipe.addClickListener(clickEvent -> {
            Recipe newRecipe = new Recipe(description.getValue(), patient.getValue(), doctor.getValue(), dateCreation.getValue(), priority.getValue());
            recipeServices.save(newRecipe);
            dataProvider.getItems().add(newRecipe);
            dataProvider.refreshAll();
            description.setValue("");
            patient.setValue(null);
            doctor.setValue(null);
            dateCreation.setValue(null);
            close();
        });

        horizontalLayout.addComponent(new Button("Cancel", event -> close()));

    }
}
