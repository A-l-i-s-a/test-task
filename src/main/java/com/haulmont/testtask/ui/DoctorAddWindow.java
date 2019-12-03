package com.haulmont.testtask.ui;

import com.haulmont.testtask.models.Doctor;
import com.haulmont.testtask.models.Specialization;
import com.haulmont.testtask.services.Services;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.*;

public class DoctorAddWindow extends Window {
    VerticalLayout layout = new VerticalLayout();

    //Create a TextField and ComboBox for class fields
    TextField name = new TextField("Name");
    TextField surname = new TextField("Surname");
    TextField patronymic = new TextField("Patronymic");
    ComboBox<Specialization> specialization = new ComboBox<>("Specialization");

    //Create a button to save the patient in the database
    Button addPatient = new Button("Ok");

    HorizontalLayout horizontalLayout = new HorizontalLayout();

    public DoctorAddWindow(ListDataProvider<Doctor> dataProvider, Services<Doctor> doctorServices) {
        super("Add doctor");
        center();
        setModal(true);

        setContent(layout);

        layout.addComponent(name);
        layout.addComponent(surname);
        layout.addComponent(patronymic);
        layout.addComponent(specialization);

        specialization.setItems(Specialization.values());

        layout.addComponent(horizontalLayout);

        horizontalLayout.addComponent(addPatient);
        addPatient.addClickListener(clickEvent -> {
            Doctor newPatient = new Doctor(name.getValue(), surname.getValue(), patronymic.getValue(), specialization.getValue());
            doctorServices.save(newPatient);
            dataProvider.getItems().add(newPatient);
            dataProvider.refreshAll();
            name.setValue("");
            surname.setValue("");
            patronymic.setValue("");
            specialization.setValue(null);
            close();
        });

        horizontalLayout.addComponent(new Button("Cancel", event -> close()));

    }
}
