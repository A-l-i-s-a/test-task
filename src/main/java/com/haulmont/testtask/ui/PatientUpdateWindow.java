package com.haulmont.testtask.ui;

import com.haulmont.testtask.models.Patient;
import com.haulmont.testtask.services.Services;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.data.validator.BeanValidator;
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

        binder.forField(name).withValidator(new BeanValidator(Patient.class, "name")).bind(Patient::getName, Patient::setName);
        binder.forField(surname).withValidator(new BeanValidator(Patient.class, "surname")).bind(Patient::getSurname, Patient::setSurname);
        binder.forField(patronymic).withValidator(new BeanValidator(Patient.class, "patronymic")).bind(Patient::getPatronymic, Patient::setPatronymic);
        binder.forField(phone).withValidator(new BeanValidator(Patient.class, "phone")).bind(Patient::getPhone, Patient::setPhone);

        binder.readBean(patient);

        updatePatient.addClickListener(clickEvent -> {
            if (binder.isValid()) {
                try {

                    binder.writeBean(patient);
                    patientServices.update(patient);
                    dataProvider.refreshAll();
                    close();

                } catch (ValidationException e) {
                    Notification.show("Patient could not be saved, " +
                            "please check field.");
                    e.printStackTrace();
                }
            } else {
                Notification.show("Patient could not be saved, " +
                        "please check field.");
            }


        });

        horizontalLayout.addComponent(new Button("Cancel", event -> close()));

    }
}
