package com.haulmont.testtask.ui;

import com.haulmont.testtask.dao.DoctorDAOImpl;
import com.haulmont.testtask.models.Doctor;
import com.haulmont.testtask.models.Specialization;
import com.haulmont.testtask.services.Services;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;


public class DoctorView extends VerticalLayout {

    Grid<Doctor> grid = new Grid<>(Doctor.class);

    //Create a TextField and ComboBox for class fields
    TextField name = new TextField("Name");
    TextField surname = new TextField("Surname");
    TextField patronymic = new TextField("Patronymic");
    ComboBox<Specialization> specialization = new ComboBox<>("Specialization");

    // Create a sub-window and set the content
    Window subWindow = new Window("Add dok");

    VerticalLayout subContent = new VerticalLayout();

    //Create a button to add a doctor to the database
    Button add = new Button("Add");

    //Create a button to delete a doctor from the database
    Button delete = new Button("Delete");

    //Create a button to update doctor information in the database
    Button update = new Button("Update");

    //Create a button to show statistics a doctor
    Button showStatistics = new Button("Show statistics");

    //Create a button to save the doctor in the database
    Button addDoc = new Button("Add");

    HorizontalLayout horizontalLayout = new HorizontalLayout();

    public DoctorView() {

        Services<Doctor> doctorServices = new Services<>(new DoctorDAOImpl());
        ListDataProvider<Doctor> dataProvider = new ListDataProvider<>(doctorServices.findAll());

        grid.setDataProvider(dataProvider);
        grid.setSizeFull();
        grid.setColumnOrder("id", "name", "surname", "patronymic", "specialization");
        grid.getColumn("recipes").setHidden(true);
        grid.getEditor().setEnabled(true);

        addComponent(grid);
        setExpandRatio(grid, 1);

        Doctor doctor = new Doctor("name1", "surname1", "patronymic1", Specialization.DENTIST);
        Doctor doctor1 = new Doctor("name", "surname", "patronymic", Specialization.NEUROLOGIST);
        Doctor doctor2 = new Doctor("name3", "surname3", "patronymic3", Specialization.OPHTHALMOLOGIST);
        Doctor doctor3 = new Doctor("name4", "surname4", "patronymic4", Specialization.PEDIATRICIAN);
        Doctor doctor4 = new Doctor("name6", "surname6", "patronymic6", Specialization.SURGEON);
        Doctor doctor5 = new Doctor("name5", "surname5", "patronymic5", Specialization.THERAPIST);

        doctorServices.save(doctor);
        doctorServices.save(doctor1);
        doctorServices.save(doctor2);
        doctorServices.save(doctor3);
        doctorServices.save(doctor4);
        doctorServices.save(doctor5);

        subWindow.setContent(subContent);
        subWindow.setModal(true);
        subWindow.center();

        specialization.setItems(Specialization.values());

        subContent.addComponent(name);
        subContent.addComponent(surname);
        subContent.addComponent(patronymic);
        subContent.addComponent(specialization);

        subContent.addComponent(addDoc);
        addDoc.addClickListener(clickEvent -> {
            Doctor newDoctor = new Doctor(name.getValue(), surname.getValue(), patronymic.getValue(), specialization.getValue());
            doctorServices.save(newDoctor);
            dataProvider.getItems().add(newDoctor);
            dataProvider.refreshAll();
            name.setValue("");
            surname.setValue("");
            patronymic.setValue("");
            specialization.setValue(null);
            subWindow.close();
        });

        add.addClickListener(clickEvent -> {
                    // Open it in the UI
                    getUI().addWindow(subWindow);
                }
        );

        delete.addClickListener(clickEvent ->
                Notification.show("You delete doctor 0_0"));

        update.addClickListener(clickEvent ->
                Notification.show("You update doctor -_-"));

        showStatistics.addClickListener(clickEvent ->
                Notification.show("Well look"));

        horizontalLayout.addComponent(add);
        horizontalLayout.addComponent(update);
        horizontalLayout.addComponent(delete);
        horizontalLayout.addComponent(showStatistics);

        addComponent(horizontalLayout);
    }
}
