package com.haulmont.testtask.ui;

import com.haulmont.testtask.models.Patient;
import com.haulmont.testtask.services.Services;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.*;

public class PatientUpdateWindow extends Window {

    VerticalLayout layout = new VerticalLayout();

    TextField name = new TextField("Name");
    TextField surname = new TextField("Surname");
    TextField patronymic = new TextField("Patronymic");
    TextField phone = new TextField("Phone");

    //Create a button to save the patient in the database
    Button updatePatient = new Button("Ok");

    HorizontalLayout horizontalLayout = new HorizontalLayout();

    public PatientUpdateWindow(ListDataProvider<Patient> dataProvider, Services<Patient> patientServices, Patient patient) {
        super("Update patient");
        center();
        setModal(true);

        setContent(layout);

        layout.addComponent(name);
        layout.addComponent(surname);
        layout.addComponent(patronymic);
        layout.addComponent(phone);

        layout.addComponent(horizontalLayout);

        horizontalLayout.addComponent(updatePatient);

        Binder<Patient> binder = new Binder<>();

        binder.bind(name, Patient::getName, Patient::setName);
        binder.bind(surname, Patient::getSurname, Patient::setSurname);
        binder.bind(patronymic, Patient::getPatronymic, Patient::setPatronymic);
        binder.bind(phone, Patient::getPhone, Patient::setPhone);

        binder.readBean(patient);

        updatePatient.addClickListener(clickEvent -> {
            try {

                binder.writeBean(patient);
                patientServices.update(patient);
                dataProvider.refreshAll();

            } catch (ValidationException e) {
                Notification.show("Patient could not be saved, " +
                        "please check error messages for each field.");
            }

            close();
        });

        horizontalLayout.addComponent(new Button("Cancel", event -> close()));

    }
}
