package com.haulmont.testtask.ui;

import com.haulmont.testtask.models.Doctor;
import com.haulmont.testtask.models.Patient;
import com.haulmont.testtask.models.Priority;
import com.haulmont.testtask.models.Recipe;
import com.haulmont.testtask.services.Services;
import com.vaadin.data.Binder;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.data.validator.BeanValidator;
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

        Binder<Recipe> binder = new Binder<>();

        binder.forField(description).withValidator(new BeanValidator(Recipe.class, "description")).bind(Recipe::getDescription, Recipe::setDescription);
        binder.forField(patient).withValidator(new BeanValidator(Recipe.class, "patient")).bind(Recipe::getPatient, Recipe::setPatient);
        binder.forField(doctor).withValidator(new BeanValidator(Recipe.class, "doctor")).bind(Recipe::getDoctor, Recipe::setDoctor);
        binder.forField(dateCreation).withValidator(new BeanValidator(Recipe.class, "dateCreation")).bind(Recipe::getDateCreation, Recipe::setDateCreation);
        binder.forField(priority).withValidator(new BeanValidator(Recipe.class, "priority")).bind(Recipe::getPriority, Recipe::setPriority);

        layout.addComponent(horizontalLayout);
        horizontalLayout.addComponent(addRecipe);
        addRecipe.addClickListener(clickEvent -> {
            if (binder.isValid()) {
                Recipe newRecipe = new Recipe(description.getValue(), patient.getValue(), doctor.getValue(), dateCreation.getValue(), priority.getValue());
                recipeServices.save(newRecipe);
                dataProvider.getItems().add(newRecipe);
                dataProvider.refreshAll();
                description.clear();
                patient.clear();
                doctor.clear();
                dateCreation.clear();
                close();
            } else {
                Notification.show("Check the fields are filled in correctly");
            }
        });

        horizontalLayout.addComponent(new Button("Cancel", event -> close()));

    }
}
