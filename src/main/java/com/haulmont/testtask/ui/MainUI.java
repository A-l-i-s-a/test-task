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

    }
}