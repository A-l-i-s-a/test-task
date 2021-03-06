package com.haulmont.testtask.services;

import com.haulmont.testtask.dao.DAO;

import java.util.List;

public class Services<T> {

    private DAO<T> dao;

    public Services(DAO<T> dao) {
        this.dao = dao;
    }

    public T find(Long id) {
        return dao.findById(id);
    }

    public List<T> findAll() {
        return dao.findAll();
    }

    public void save(T object) {
        dao.save(object);
    }

    public void delete(T object) {
        dao.delete(object);
    }

    public void update(T object) {
        dao.update(object);
    }
}
