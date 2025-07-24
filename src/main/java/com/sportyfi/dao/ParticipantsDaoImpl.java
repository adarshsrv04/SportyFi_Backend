package com.sportyfi.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sportyfi.entity.Participants;

@Repository
public class ParticipantsDaoImpl implements ParticipantsDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Participants> fetchAllParticipants(UUID matchId) {
		Session session = sessionFactory.openSession();
	    Transaction tx = null;
	    List<Participants> results = new ArrayList<>();

	    try {
	        tx = session.beginTransaction();
	        
	        StringBuilder queryStr = new StringBuilder("SELECT * FROM participants");
	        
	        if (matchId != null) {
	            queryStr.append(" WHERE match_id = :matchId");
	        }	        
	        Query<Participants> query = session.createNativeQuery(queryStr.toString(), Participants.class);
	        if (matchId != null) {
	            query.setParameter("matchId", matchId);
	        }
	        
	        results = query.getResultList();
	        tx.commit();
	    } catch (Exception e) {
	        if (tx != null) tx.rollback();
	        e.printStackTrace();
	    } finally {
	        session.close();
	    }

	    return results;
	}

	@Override
	public boolean addParticipant(Participants participant) {
		Transaction tx = null;
	    boolean isAdded = false;
//	    System.out.println(participant.getId());
//	    System.out.println(participant.getUser_id());
	    try (Session session = sessionFactory.openSession()) {
	        tx = session.beginTransaction();

	        session.persist(participant);  // Hibernate handles the SQL generation
	        tx.commit();
	        isAdded = true;

	    } catch (Exception e) {
	        if (tx != null) tx.rollback();
	        e.printStackTrace();
	    }

	    return isAdded;
	}

	@Override
	public boolean deleteParticipant(UUID participantId) {
		Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            Participants participant = session.find(Participants.class, participantId);
            if (participant != null) {
                session.remove(participant);
                tx.commit();
                return true;
            } else {
                tx.rollback(); // No such participant
                return false;
            }
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
	}
}
