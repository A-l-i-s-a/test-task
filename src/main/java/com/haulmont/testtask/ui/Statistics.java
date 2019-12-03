package com.haulmont.testtask.ui;

import com.haulmont.testtask.dao.DoctorDAOImpl;
import com.haulmont.testtask.models.Doctor;
import com.haulmont.testtask.services.Services;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class Statistics extends Window {

    Services<Doctor> doctorServices = new Services<>(new DoctorDAOImpl());
    ListDataProvider<Doctor> dataProvider = new ListDataProvider<>(doctorServices.findAll());
    VerticalLayout layout = new VerticalLayout();
    Grid<Doctor> doctorGrid = new Grid<>();

    public Statistics() {
        super("Statistics");
        center();
        setModal(true);

        setContent(layout);
        layout.setWidth("600px");

        layout.addComponent(doctorGrid);
        doctorGrid.setDataProvider(dataProvider);
        doctorGrid.setSizeFull();
        doctorGrid.addColumn(doctor -> doctor.getName() + " " + doctor.getSurname() + " " + doctor.getPatronymic()).setCaption("Doctor");
        doctorGrid.addColumn(doctor -> doctor.getRecipes().size()).setCaption("Number of recipes");
        layout.setExpandRatio(doctorGrid, 1);

    }
}
