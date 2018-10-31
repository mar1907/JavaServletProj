package com.ds.assignment1_2.dao;

import com.ds.assignment1_2.entity.City;
import org.hibernate.*;

import java.util.ArrayList;
import java.util.List;

public class CityDAO {

    private SessionFactory factory;

    public CityDAO(SessionFactory factory){
        this.factory = factory;
    }

    public City findCityByName(String name){
        Session session = factory.openSession();
        Transaction tx = null;
        List<City> cities = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM City Where name = :name");
            query.setParameter("name", name);
            cities = query.list();
            tx.commit();
        } catch (HibernateException e){
            if(tx!=null){
                tx.rollback();
            }
        } finally {
            session.close();
        }

        return cities != null && !cities.isEmpty() ? cities.get(0) : null;
    }

    public List<City> findAllCities(){
        Session session = factory.openSession();
        Transaction tx = null;
        List<City> cities = new ArrayList<>();
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM City");
            cities = query.list();
            tx.commit();
        } catch (HibernateException e){
            if(tx!=null){
                tx.rollback();
            }
        } finally {
            session.close();
        }

        return cities;
    }
}
