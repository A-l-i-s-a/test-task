package com.haulmont.testtask.ui;

import com.haulmont.testtask.dao.DoctorDAOImpl;
import com.haulmont.testtask.models.Doctor;
import com.haulmont.testtask.models.Recipe;
import com.haulmont.testtask.models.Specialization;
import com.haulmont.testtask.services.Services;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.*;
import org.hibernate.exception.ConstraintViolationException;


public class DoctorView extends VerticalLayout {

    Grid<Doctor> grid = new Grid<>(Doctor.class);

    //Create a TextField and ComboBox for class fields
    TextField name = new TextField("Name");
    TextField surname = new TextField("Surname");
    TextField patronymic = new TextField("Patronymic");
    ComboBox<Specialization> specialization = new ComboBox<>("Specialization");

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

        addComponent(grid);
        setExpandRatio(grid, 1);

        specialization.setItems(Specialization.values());

        add.addClickListener(clickEvent -> {
                    // Open it in the UI
                    getUI().addWindow(new DoctorAddWindow(dataProvider, doctorServices));
                }
        );

        delete.addClickListener(clickEvent -> {
            Doctor doctor = null;

            for (Doctor doctorFromSet : grid.getSelectedItems()) {
                doctor = doctorFromSet;
            }

            if (doctor != null) {
                try {
                    doctorServices.delete(doctor);
                    dataProvider.refreshAll();
                } catch (Exception exception) {
                    Notification.show("Could not execute statement. " + exception);
                }
            }

        });

        update.addClickListener(clickEvent -> {
            Doctor doctor = null;

            for (Doctor doctorFromSet : grid.getSelectedItems()) {
                doctor = doctorFromSet;
            }

            if (doctor != null) {
                getUI().addWindow(new DoctorUpdateWindow(dataProvider, doctorServices, doctor));
            } else {
                Notification.show("Select line");
            }
        });

        showStatistics.addClickListener(clickEvent -> {
                getUI().addWindow(new Statistics());
        });

        horizontalLayout.addComponent(add);
        horizontalLayout.addComponent(update);
        horizontalLayout.addComponent(delete);
        horizontalLayout.addComponent(showStatistics);

        addComponent(horizontalLayout);
    }
}
