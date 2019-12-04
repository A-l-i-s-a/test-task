package com.haulmont.testtask.ui;

import com.haulmont.testtask.models.Patient;
import com.haulmont.testtask.services.Services;
import com.vaadin.data.Binder;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.data.validator.BeanValidator;
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

        Binder<Patient> binder = new Binder<>();

        binder.forField(name).withValidator(new BeanValidator(Patient.class, "name")).bind(Patient::getName, Patient::setName);
        binder.forField(surname).withValidator(new BeanValidator(Patient.class, "surname")).bind(Patient::getSurname, Patient::setSurname);
        binder.forField(patronymic).withValidator(new BeanValidator(Patient.class, "patronymic")).bind(Patient::getPatronymic, Patient::setPatronymic);
        binder.forField(phone).withValidator(new BeanValidator(Patient.class, "phone")).bind(Patient::getPhone, Patient::setPhone);

        horizontalLayout.addComponent(addPatient);
        addPatient.addClickListener(clickEvent -> {
            if (binder.isValid()) {
                Patient newPatient = new Patient(name.getValue(), surname.getValue(), patronymic.getValue(), phone.getValue());
                patientServices.save(newPatient);
                dataProvider.getItems().add(newPatient);
                dataProvider.refreshAll();
                name.clear();
                surname.clear();
                patronymic.clear();
                phone.clear();
                close();
            } else {
                Notification.show("Check the fields are filled in correctly");
            }
        });

        horizontalLayout.addComponent(new Button("Cancel", event -> close()));
    }
}
