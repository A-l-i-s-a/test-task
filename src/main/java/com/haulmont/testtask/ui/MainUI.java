package com.haulmont.testtask.ui;

import com.haulmont.testtask.dao.DoctorDAOImpl;
import com.haulmont.testtask.dao.PatientDAOImpl;
import com.haulmont.testtask.dao.RecipeDAOImpl;
import com.haulmont.testtask.models.*;
import com.haulmont.testtask.services.Services;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {

    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setMargin(true);
        setContent(layout);


        TabSheet tabsheet = new TabSheet();
        layout.addComponent(tabsheet);

        tabsheet.addTab(new PatientView(), "Patient");
        tabsheet.addTab(new DoctorView(), "Doctor");
        tabsheet.addTab(new RecipeView(), "Recipe");

        Services<Recipe> recipeServices = new Services<>(new RecipeDAOImpl());
        Services<Doctor> doctorServices = new Services<>(new DoctorDAOImpl());
        Services<Patient> patientServices = new Services<>(new PatientDAOImpl());

        Doctor doctor = new Doctor("name1", "surname1", "patronymic1", Specialization.DENTIST);
        Doctor doctor1 = new Doctor("name", "surname", "patronymic", Specialization.NEUROLOGIST);
        Doctor doctor2 = new Doctor("name3", "surname3", "patronymic3", Specialization.OPHTHALMOLOGIST);
        Doctor doctor3 = new Doctor("name4", "surname4", "patronymic4", Specialization.PEDIATRICIAN);
        Doctor doctor4 = new Doctor("name6", "surname6", "patronymic6", Specialization.SURGEON);
        Doctor doctor5 = new Doctor("name5", "surname5", "patronymic5", Specialization.THERAPIST);

        Patient patient = new Patient("name1", "surname1", "patronymic1", "1234567");
        Patient patient1 = new Patient("name", "surname", "patronymic", "9867537");
        Patient patient2 = new Patient("name3", "surname3", "patronymic3", "9865334");

        Recipe recipe1 = new Recipe("hjekdnfvlmdfvml", patientServices.find((long) 1), doctorServices.find((long) 1), LocalDate.now(), Priority.CITO );
        Recipe recipe2 = new Recipe("dsfbv do ndv dflm dfdfgvgd ml", patientServices.find((long) 2), doctorServices.find((long) 1), LocalDate.now(), Priority.NORMAL );

        doctorServices.save(doctor);
        doctorServices.save(doctor1);
        doctorServices.save(doctor2);
        doctorServices.save(doctor3);
        doctorServices.save(doctor4);
        doctorServices.save(doctor5);

        patientServices.save(patient);
        patientServices.save(patient1);
        patientServices.save(patient2);

        recipeServices.save(recipe1);
        recipeServices.save(recipe2);


    }
}