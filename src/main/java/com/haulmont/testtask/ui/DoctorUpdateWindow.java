package com.haulmont.testtask.ui;

import com.haulmont.testtask.models.Doctor;
import com.haulmont.testtask.models.Patient;
import com.haulmont.testtask.models.Specialization;
import com.haulmont.testtask.services.Services;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.*;

public class DoctorUpdateWindow extends Window {

    VerticalLayout layout = new VerticalLayout();

    //Create a TextField and ComboBox for class fields
    TextField name = new TextField("Name");
    TextField surname = new TextField("Surname");
    TextField patronymic = new TextField("Patronymic");
    ComboBox<Specialization> specialization = new ComboBox<>("Specialization");

    //Create a button to save the patient in the database
    Button updatePatient = new Button("Ok");

    HorizontalLayout horizontalLayout = new HorizontalLayout();

    public DoctorUpdateWindow(ListDataProvider<Doctor> dataProvider, Services<Doctor> doctorServices, Doctor doctor) {
        super("Update patient");
        center();
        setModal(true);

        setContent(layout);

        layout.addComponent(name);
        layout.addComponent(surname);
        layout.addComponent(patronymic);
        layout.addComponent(specialization);

        specialization.setItems(Specialization.values());

        layout.addComponent(horizontalLayout);

        horizontalLayout.addComponent(updatePatient);

        Binder<Doctor> binder = new Binder<>();

        binder.bind(name, Doctor::getName, Doctor::setName);
        binder.bind(surname, Doctor::getSurname, Doctor::setSurname);
        binder.bind(patronymic, Doctor::getPatronymic, Doctor::setPatronymic);
        binder.bind(specialization, Doctor::getSpecialization, Doctor::setSpecialization);

        binder.readBean(doctor);

        updatePatient.addClickListener(clickEvent -> {
            try {

                binder.writeBean(doctor);
                doctorServices.update(doctor);
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
