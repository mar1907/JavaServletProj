package com.ds.assignment1_2.dao;

import com.ds.assignment1_2.entity.User;
import org.hibernate.*;

import java.util.List;

public class UserDAO {

    private SessionFactory factory;

    public UserDAO(SessionFactory factory) {
        this.factory = factory;
    }

    public User getUser(String username){
        Session session = factory.openSession();
        Transaction tx = null;
        List<User> users = null;
        try{
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM User WHERE " +
                    "username = :username");
            query.setParameter("username", username);
            users = query.list();
            tx.commit();
        } catch (HibernateException e){
            if(tx != null){
                tx.rollback();
            }
        } finally {
            session.close();
        }

        return users != null && !users.isEmpty() ? users.get(0) : null;
    }
}
