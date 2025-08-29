package com.sportyfi.dao;

import java.util.List;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sportyfi.entity.Bookings;
import com.sportyfi.entity.Matches;

@Repository
public class VenueBookingDao {

	@Autowired
    private SessionFactory sessionFactory;

//	@Transactional
    public Bookings saveBooking(Bookings booking) {
		Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
	        tx = session.beginTransaction();

	        session.persist(booking);  // Hibernate handles the SQL generation
	        tx.commit();
	        return booking;
	    } catch (Exception e) {
	        if (tx != null) tx.rollback();
	        e.printStackTrace();
	    }
//		sessionFactory.getCurrentSession().persist(booking);
        return booking;
	}
    
    public List<Bookings> findAllBookings(UUID userId) {
    	try (Session session = sessionFactory.openSession()) {
    		String hql = "FROM Bookings b WHERE b.user_id = :userId";
            Query<Bookings> query = session.createQuery(hql);
            query.setParameter("userId", userId);
            return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
}
