package com.haulmont.testtask.services;

import com.haulmont.testtask.dao.DoctorDAOImpl;
import com.haulmont.testtask.dao.PatientDAOImpl;
import com.haulmont.testtask.dao.RecipeDAOImpl;
import com.haulmont.testtask.models.*;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class ServicesTest {

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


    @Test
    public void find() {

    }

    @Test
    public void findAll() {
        save();

        for (Doctor doctor6 : doctorServices.findAll()) {
            System.out.println(doctor6);
        }

        System.out.println("---------------------------------------------");

        for (Patient patient3 : patientServices.findAll()) {
            System.out.println(patient3);
        }

        System.out.println("---------------------------------------------");

        for (Recipe recipe : recipeServices.findAll()) {
            System.out.println(recipe);
        }
    }

    @Test
    public void save() {
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

    @Test
    public void delete() {
        save();

        List<Doctor> beforeD = doctorServices.findAll();
        doctorServices.delete(doctor5);
        List<Doctor> afterD = doctorServices.findAll();

        assertTrue(beforeD.size() > afterD.size());

        List<Patient> beforeP = patientServices.findAll();
        patientServices.delete(patient2);
        List<Patient> afterP = patientServices.findAll();

        assertTrue(beforeP.size() > afterP.size());

        List<Recipe> beforeR = recipeServices.findAll();
        recipeServices.delete(recipe1);
        List<Recipe> afterR = recipeServices.findAll();

        assertTrue(beforeR.size() > afterR.size());
    }

    @Test
    public void update() {
        save();

    }
}