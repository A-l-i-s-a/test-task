package com.haulmont.testtask.ui;

import com.haulmont.testtask.models.Patient;
import com.haulmont.testtask.services.Services;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.*;

public class PatientAddWindow extends Window {
    VerticalLayout layout = new VerticalLayout();

    TextField name = new TextField("Name");
    TextField surname = new TextField("Surname");
    TextField patronymic = new TextField("Patronymic");
    TextField phone = new TextField("Phone");

    //Create a button to save the patient in the database
    Button addPatient = new Button("Ok");

    HorizontalLayout horizontalLayout = new HorizontalLayout();

    public PatientAddWindow(ListDataProvider<Patient> dataProvider, Services<Patient> patientServices) {
        super("Add patient");
        center();
        setModal(true);

        setContent(layout);

        layout.addComponent(name);
        layout.addComponent(surname);
        layout.addComponent(patronymic);
        layout.addComponent(phone);

        layout.addComponent(horizontalLayout);

        horizontalLayout.addComponent(addPatient);
        addPatient.addClickListener(clickEvent -> {
            Patient newPatient = new Patient(name.getValue(), surname.getValue(), patronymic.getValue(), phone.getValue());
            patientServices.save(newPatient);
            dataProvider.getItems().add(newPatient);
            dataProvider.refreshAll();
            name.setValue("");
            surname.setValue("");
            patronymic.setValue("");
            phone.setValue("");
            close();
        });

        horizontalLayout.addComponent(new Button("Cancel", event -> close()));
    }
}
