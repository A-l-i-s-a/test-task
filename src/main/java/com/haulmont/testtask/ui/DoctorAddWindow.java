package com.haulmont.testtask.ui;

import com.haulmont.testtask.models.Doctor;
import com.haulmont.testtask.models.Specialization;
import com.haulmont.testtask.services.Services;
import com.vaadin.data.Binder;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.data.validator.BeanValidator;
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

        Binder<Doctor> binder = new Binder<>();
        binder.forField(name).withValidator(new BeanValidator(Doctor.class, "name")).bind(Doctor::getName, Doctor::setName);
        binder.forField(surname).withValidator(new BeanValidator(Doctor.class, "surname")).bind(Doctor::getSurname, Doctor::setSurname);
        binder.forField(patronymic).withValidator(new BeanValidator(Doctor.class, "patronymic")).bind(Doctor::getPatronymic, Doctor::setPatronymic);
        binder.forField(specialization).withValidator(new BeanValidator(Doctor.class, "specialization")).bind(Doctor::getSpecialization, Doctor::setSpecialization);


        horizontalLayout.addComponent(addPatient);
        addPatient.addClickListener(clickEvent -> {
            if (binder.isValid()) {
                Doctor newDoctor = new Doctor(name.getValue(), surname.getValue(), patronymic.getValue(), specialization.getValue());
                doctorServices.save(newDoctor);
                dataProvider.getItems().add(newDoctor);
                dataProvider.refreshAll();
                name.clear();
                surname.clear();
                patronymic.clear();
                specialization.clear();
                close();
            } else {
                Notification.show("Check the fields are filled in correctly");
            }
        });

        horizontalLayout.addComponent(new Button("Cancel", event -> close()));

    }
}
