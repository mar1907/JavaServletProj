package com.ds.assignment1_2.dao;

import com.ds.assignment1_2.entity.Flight;
import org.hibernate.*;

import java.util.ArrayList;
import java.util.List;

public class FlightDAO {

    private SessionFactory factory;

    public FlightDAO(SessionFactory factory) {
        this.factory = factory;
    }

    public Flight addFlight(Flight flight){
        int flightId = -1;
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            flightId = (Integer) session.save(flight);
            flight.setId(flightId);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null){
                tx.rollback();
            }
        } finally {
            session.close();
        }

        return flight;
    }

    public List<Flight> findFlights() {
        Session session = factory.openSession();
        Transaction tx = null;
        List<Flight> flights = new ArrayList<>();
        try {
            tx = session.beginTransaction();
            flights = session.createQuery("FROM Flight").list();
            tx.commit();
            for(Flight flight: flights){
                Hibernate.initialize(flight.getArrivalCity());
                Hibernate.initialize(flight.getDepartureCity());
            }
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }

        return flights;
    }

    public Flight findFlight(int id) {
        Session session = factory.openSession();
        Transaction tx = null;
        List<Flight> flights = new ArrayList<>();
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM Flight WHERE id = :id");
            query.setParameter("id", id);
            flights = query.list();
            tx.commit();
            for(Flight flight: flights){
                Hibernate.initialize(flight.getArrivalCity());
                Hibernate.initialize(flight.getDepartureCity());
            }
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
        return flights != null && !flights.isEmpty() ? flights.get(0) : null;
    }

    public Flight deleteFlight(int id){
        Session session = factory.openSession();
        Transaction tx = null;
        Flight flight = findFlight(id);
        if(flight == null) return null;
        try {
            tx = session.beginTransaction();
            session.delete(flight);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
        return flight;
    }

    public Flight updateFlight(Flight flight){
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(flight);
            tx.commit();
        } catch (HibernateException e){
            if(tx != null){
                tx.rollback();
            }
        } finally {
            session.close();
        }
        return flight;
    }
}
