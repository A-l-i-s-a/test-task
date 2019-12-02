package com.haulmont.testtask.dao;

import com.haulmont.testtask.models.Patient;
import com.haulmont.testtask.models.Recipe;
import com.haulmont.testtask.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class RecipeDAOImpl extends DAO<Recipe> {
    @Override
    public Recipe findById(Long id) {
        Session session = null;
        Recipe recipe = null;
        try {
            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            recipe = session.load(Recipe.class, id);
        } catch (Exception e) {
            System.out.println("findById error: " + e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return recipe;
    }

    @Override
    public List<Recipe> findAll() {
        Session session = null;
        List<Recipe> recipes = new ArrayList<>();
        try {
            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            CriteriaQuery<Recipe> criteriaQuery = session.getCriteriaBuilder().createQuery(Recipe.class);
            criteriaQuery.from(Recipe.class);
            recipes = session.createQuery(criteriaQuery).getResultList();

        } catch (Exception e) {

        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return recipes;
    }
}
