package com.haulmont.testtask.ui;

import com.haulmont.testtask.dao.PatientDAOImpl;
import com.haulmont.testtask.models.Patient;
import com.haulmont.testtask.services.Services;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;

public class PatientView extends VerticalLayout {

    Grid<Patient> grid = new Grid<>(Patient.class);

    //Create a button to add a patient to the database
    Button add = new Button("Add");

    //Create a button to delete a patient from the database
    Button delete = new Button("Delete");

    //Create a button to update patient information in the database
    Button update = new Button("Update");

    Window subWindow = new Window("Add dok");
    VerticalLayout subContent = new VerticalLayout();

    TextField name = new TextField("Name");
    TextField surname = new TextField("Surname");
    TextField patronymic = new TextField("Patronymic");
    TextField phone = new TextField("Phone");

    //Create a button to save the doctor in the database
    Button addDoc = new Button("Add");

    HorizontalLayout horizontalLayout = new HorizontalLayout();

    public PatientView() {

        Services<Patient> patientServices = new Services<>(new PatientDAOImpl());
        ListDataProvider<Patient> dataProvider = new ListDataProvider<>(patientServices.findAll());

        addComponent(grid);

        horizontalLayout.addComponent(add);
        horizontalLayout.addComponent(update);
        horizontalLayout.addComponent(delete);

        addComponent(horizontalLayout);

        grid.setDataProvider(dataProvider);
        grid.setSizeFull();
        grid.setColumnOrder("id", "name", "surname", "patronymic", "phone");
        grid.getColumn("recipes").setHidden(true);

        subWindow.setContent(subContent);
        subWindow.setModal(true);
        subWindow.center();

        subContent.addComponent(name);
        subContent.addComponent(surname);
        subContent.addComponent(patronymic);
        subContent.addComponent(phone);

        Patient doctor = new Patient("name1", "surname1", "patronymic1", "1234567");
        Patient doctor1 = new Patient("name", "surname", "patronymic", "9867537");
        Patient doctor2 = new Patient("name3", "surname3", "patronymic3", "9865334");

        patientServices.save(doctor);
        patientServices.save(doctor1);
        patientServices.save(doctor2);

        subContent.addComponent(addDoc);
        addDoc.addClickListener(clickEvent -> {
            Patient newPatient = new Patient(name.getValue(), surname.getValue(), patronymic.getValue(), phone.getValue());
            patientServices.save(newPatient);
            dataProvider.getItems().add(newPatient);
            dataProvider.refreshAll();
            name.setValue("");
            surname.setValue("");
            patronymic.setValue("");
            phone.setValue("");
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


    }
}
