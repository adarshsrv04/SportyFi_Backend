package com.sportyfi.dao;

import com.sportyfi.entity.VenueRequest;
import com.sportyfi.entity.Venues;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
@Transactional
public class VenueDao {

    private final SessionFactory sessionFactory;

    public VenueDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
 
    public Venues save(Venues venue) {
//        Session session = sessionFactory.getCurrentSession();
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(venue);
        tx.commit();
        session.close();
        
        return venue;
    }

    public Venues findById(UUID id) {
    	Session session = sessionFactory.openSession();
        Venues venue = session.get(Venues.class, id);
        session.close();
        return venue;
    }

	public List<Venues> findAll() {
		Session session = sessionFactory.openSession();
//        Transaction tx = session.beginTransaction();
		List<Venues> venues =  session
                .createQuery("FROM Venues", Venues.class)
                .list();
		session.close();
		return venues;
	}
}

