/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.service;

import com.forall.modell.DataProxy;
import java.util.List;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jd
 */
@Stateless
public class DataService {

    @PersistenceContext
    private EntityManager entityManager;

    @Asynchronous
    public void save(double data, long time) {
        DataProxy proxy = new DataProxy(data);
        proxy.setTimeStamp(time);
        entityManager.persist(proxy);
    }

    public DataProxy findById(final long id) {
        return (DataProxy) entityManager.createQuery("SELECT d FROM DataProxy d WHERE d.id = :id").setParameter("id", id).getSingleResult();
    }

    public List<DataProxy> get(long from, long to) {

        List<DataProxy> result = entityManager.createQuery("SELECT d FROM DataProxy d WHERE d.id >= :from AND d.id <= :to").setParameter("from", from).setParameter("to", to).getResultList();
        return result;
    }
}