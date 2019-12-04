package com.haulmont.testtask.ui;

import com.haulmont.testtask.models.Doctor;
import com.haulmont.testtask.models.Specialization;
import com.haulmont.testtask.services.Services;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.data.validator.BeanValidator;
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

        binder.forField(name).withValidator(new BeanValidator(Doctor.class, "name")).bind(Doctor::getName, Doctor::setName);
        binder.forField(surname).withValidator(new BeanValidator(Doctor.class, "surname")).bind(Doctor::getSurname, Doctor::setSurname);
        binder.forField(patronymic).withValidator(new BeanValidator(Doctor.class, "patronymic")).bind(Doctor::getPatronymic, Doctor::setPatronymic);
        binder.forField(specialization).withValidator(new BeanValidator(Doctor.class, "specialization")).bind(Doctor::getSpecialization, Doctor::setSpecialization);

        binder.readBean(doctor);

        updatePatient.addClickListener(clickEvent -> {
                if (binder.isValid()) {
                    try {
                        binder.writeBean(doctor);
                        doctorServices.update(doctor);
                        dataProvider.refreshAll();
                        close();
                    } catch (ValidationException e) {
                        Notification.show("ValidationException");
                        e.printStackTrace();
                    }
                } else {
                    Notification.show("Doctor could not be saved, " +
                            "please check fields.");
                }
        });

        horizontalLayout.addComponent(new Button("Cancel", event -> close()));

    }
}
