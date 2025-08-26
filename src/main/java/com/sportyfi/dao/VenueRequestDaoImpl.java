package com.sportyfi.dao;

import java.util.List;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sportyfi.entity.VenueRequest;

@Repository
public class VenueRequestDaoImpl implements VenueRequestDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(VenueRequest request) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.save(request);
        tx.commit();
        session.close();
    }

    @Override
    public VenueRequest findById(UUID id) {
        Session session = sessionFactory.openSession();

        VenueRequest venueRequest = session.get(VenueRequest.class, id);
        session.close();
        return venueRequest;
//        return sessionFactory.getCurrentSession().get(VenueRequest.class, id);
    }

    @Override
    public List<VenueRequest> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM VenueRequest", VenueRequest.class)
                .list();
    }

    @Override
    public void update(VenueRequest request) {
        sessionFactory.getCurrentSession().update(request);
    }

    @Override
    public void delete(UUID id) {
        Session session = sessionFactory.getCurrentSession();
        VenueRequest request = session.get(VenueRequest.class, id);
        if (request != null) session.delete(request);
    }
}
