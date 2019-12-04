package com.haulmont.testtask.ui;

import com.haulmont.testtask.dao.PatientDAOImpl;
import com.haulmont.testtask.models.Patient;
import com.haulmont.testtask.services.Services;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.*;

public class PatientView extends VerticalLayout {

    Grid<Patient> grid = new Grid<>(Patient.class);

    //Create a button to add a patient to the database
    Button add = new Button("Add");

    //Create a button to delete a patient from the database
    Button delete = new Button("Delete");

    //Create a button to update patient information in the database
    Button update = new Button("Update");

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

        add.addClickListener(clickEvent -> {
                    // Open it in the UI
                    getUI().addWindow(new PatientAddWindow(dataProvider, patientServices));
                }
        );

        delete.addClickListener(clickEvent -> {
            Patient patient = null;
            for (Patient patientFromSet : grid.getSelectedItems()) {
                patient = patientFromSet;
            }
            if (patient != null) {
                try {
                    patientServices.delete(patient);
                    dataProvider.refreshAll();
                } catch (Exception exception) {
                    Notification.show("Could not execute statement. " + exception);
                }
            }
        });

        update.addClickListener(clickEvent -> {
            Patient patient = null;
            for (Patient patientFromSet : grid.getSelectedItems()) {
                patient = patientFromSet;
            }

            if (patient != null) {
                getUI().addWindow(new PatientUpdateWindow(dataProvider, patientServices, patient));
            } else {
                Notification.show("Select line");
            }
        });


    }
}
