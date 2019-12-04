package com.haulmont.testtask.services;

import com.haulmont.testtask.dao.DoctorDAOImpl;
import com.haulmont.testtask.dao.PatientDAOImpl;
import com.haulmont.testtask.dao.RecipeDAOImpl;
import com.haulmont.testtask.models.*;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertTrue;


public class ServicesTest {

    Services<Recipe> recipeServices = new Services<>(new RecipeDAOImpl());
    Services<Doctor> doctorServices = new Services<>(new DoctorDAOImpl());
    Services<Patient> patientServices = new Services<>(new PatientDAOImpl());

    Doctor doctor = new Doctor("Zacherie", "Housbie", "MacCahee", Specialization.SURGEON);
    Doctor doctor1 = new Doctor("Slade", "Pantin", "Barukh", Specialization.PSYCHOTHERAPIST);
    Doctor doctor2 = new Doctor("Ermengarde", "Garham", "Genty", Specialization.THERAPIST);
    Doctor doctor3 = new Doctor("Ettore", "Batchley", "Gutch", Specialization.NEUROLOGIST);
    Doctor doctor4 = new Doctor("Vassily", "Preist", "Rose", Specialization.PSYCHOTHERAPIST);
    Doctor doctor5 = new Doctor("Aluin", "Fantonetti", "Jagson", Specialization.DENTIST);
    Doctor doctor6 = new Doctor("Galen", "Bosman", "Eldridge", Specialization.THERAPIST);
    Doctor doctor7 = new Doctor("Georgy", "Colbridge", "Nussen", Specialization.SURGEON);
    Doctor doctor8 = new Doctor("Ardith", "Chace", "Medina", Specialization.SURGEON);
    Doctor doctor9 = new Doctor("Zilvia", "Evins", "Bellenie", Specialization.DENTIST);

    Patient patient = new Patient("Leon", "Yurocjkin", "Arrighetti", "5586343698");
    Patient patient1 = new Patient("Artemis", "Ossenna", "Coulman", "3085888564");
    Patient patient2 = new Patient("Kris", "Gatley", "Thomasen", "4206628002");
    Patient patient3 = new Patient("Wolfy", "Mallinson", "Tossell", "1242919336");
    Patient patient4 = new Patient("Rhett", "Tregonna", "Fardy", "8255312207");
    Patient patient5 = new Patient("Johnny", "Coslitt", "Sauniere", "4024683017");
    Patient patient6 = new Patient("Ronalda", "Esel", "Querrard", "6586192412");
    Patient patient7 = new Patient("Megen", "Boyett", "Antoniak", "9868367279");
    Patient patient8 = new Patient("Ashely", "Copper", "Blenkinsop", "6003287181");
    Patient patient9 = new Patient( "Allis", "Dunford", "Polin", "9513100029");

    Recipe recipe1 = new Recipe("Dolor vel est donec odio justo sollicitudin ut suscipit a feugiat et eros vestibulum ac est lacinia nisi", patientServices.find((long) 1), doctorServices.find((long) 1), LocalDate.now(), Priority.NORMAL);
    Recipe recipe2 = new Recipe("Cum sociis natoque penatibus et magnis dis parturient montes nascetur ridiculus mus vivamus vestibulum", patientServices.find((long) 1), doctorServices.find((long) 2), LocalDate.now(), Priority.CITO);


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

    public void save() {
        doctorServices.save(doctor);
        doctorServices.save(doctor1);
        doctorServices.save(doctor2);
        doctorServices.save(doctor3);
        doctorServices.save(doctor4);
        doctorServices.save(doctor5);
        doctorServices.save(doctor6);
        doctorServices.save(doctor7);
        doctorServices.save(doctor8);
        doctorServices.save(doctor9);

        patientServices.save(patient);
        patientServices.save(patient1);
        patientServices.save(patient2);
        patientServices.save(patient3);
        patientServices.save(patient4);
        patientServices.save(patient5);
        patientServices.save(patient6);
        patientServices.save(patient7);
        patientServices.save(patient8);
        patientServices.save(patient9);

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