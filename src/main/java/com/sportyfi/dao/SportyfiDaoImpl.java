package com.sportyfi.dao;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sportyfi.entity.Matches;
import com.sportyfi.entity.Participants;

import jakarta.persistence.TypedQuery;

@Repository
public class SportyfiDaoImpl implements SportyfiDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Matches> findAllMatches(String sport) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<Matches> results = new ArrayList<>();

		try {
			tx = session.beginTransaction();

			StringBuilder queryStr = new StringBuilder("SELECT * FROM matches");

			boolean hasCondition = false;
			if (sport != null && !sport.trim().isEmpty()) {
				queryStr.append(" WHERE sport = :sport");
				hasCondition = true;
			}
			//	        if (onlyUpcoming) { // Assume this boolean parameter indicates whether to filter future matches
			if (hasCondition) {
				queryStr.append(" AND match_time > NOW()");
			} else {
				queryStr.append(" WHERE match_time > NOW()");
				hasCondition = true;
			}
			//	        }
			queryStr.append(" ORDER BY match_time DESC");

			Query<Matches> query = session.createNativeQuery(queryStr.toString(), Matches.class);

			if (sport != null && !sport.trim().isEmpty()) {
				query.setParameter("sport", sport);
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
	public List<Matches> findMatchesByCity(String city) {
	    try (Session session = sessionFactory.openSession()) {
	        String hql = "FROM Matches WHERE city = :city AND match_time > :now ORDER BY match_time DESC";
	        Query<Matches> query = session.createQuery(hql, Matches.class);
	        query.setParameter("city", city);
	        query.setParameter("now", ZonedDateTime.now()); // or new Date() if using java.util.Date
	        return query.getResultList();
	    }
	}

	@Override
	public Matches findMatchById(UUID matchId) {
		try (Session session = sessionFactory.openSession()) {
			Matches matches = session.get(Matches.class, matchId);
			if(matches != null) {
				//				System.out.println(matches);
				return matches;
			}
		}
		return null;
	}

	// find match by user id
	@Override
	public List<Matches> findMatchesByUserId(UUID userId) {
		try (Session session = sessionFactory.openSession()) {
			String hql = "SELECT m FROM Matches m " +
					"JOIN Participants p ON m.id = p.match_id " +
					"WHERE p.user_id = :userId AND m.match_time > :now " +
					"ORDER BY m.match_time DESC";
			TypedQuery<Matches> query = session.createQuery(hql, Matches.class);
			query.setParameter("userId", userId);
			query.setParameter("now", ZonedDateTime.now());
			return query.getResultList();
		}
	}

	@Override
	public boolean saveMatch(Matches match) {
		Transaction tx = null;
		boolean isSaved = false;
		try (Session session = sessionFactory.openSession()) {
			tx = session.beginTransaction();
			//	        UUID hostId = match.getHostId();
			//	        User user = session.get(User.class, hostId);
			//	        if (user == null) {
			//	            throw new IllegalArgumentException("Invalid host_id: user not found");
			//	        }
			if ("Yes".equals(match.getHost_join())) {
				Integer slots = match.getAvailable_slots();
				if (slots != null && slots > 0) {
					match.setAvailable_slots(slots - 1);
				}
			}

			session.save(match);  // Hibernate handles the SQL generation

			if ("Yes".equals(match.getHost_join())) {
				Participants participant = new Participants();
				participant.setUser_id(match.getHost_id());
				participant.setMatch_id(match.getId());
				//	            participant.setCreated_at(match.getCreated_at());

				session.save(participant);
			}

			tx.commit();
			isSaved = true;

		} catch (Exception e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		}

		return isSaved;
	}


	@Override
	public boolean updateMatchField(UUID matchId, String field, String newValue) {
		// TODO Auto-generated method stub
		// Define allowed fields to avoid SQL injection
		Set<String> allowedFields = Set.of(
				"available_slots", "description", "location", "sport", 
				"skill_level", "team_size", "match_time"
				);

		if (!allowedFields.contains(field)) {
			return false;
		}

		Transaction tx = null;
		try (Session session = sessionFactory.openSession()) {
			tx = session.beginTransaction();

			String sql = "UPDATE matches SET " + field + " = :value WHERE id = :id";
			Query<?> query = session.createNativeQuery(sql);
			query.setParameter("id", matchId);

			// Handle value types properly
			if (field.equals("available_slots") || field.equals("team_size")) {
				query.setParameter("value", Integer.parseInt(newValue));
			} else if (field.equals("match_time")) {
				query.setParameter("value", Timestamp.valueOf(newValue)); // expects "yyyy-MM-dd HH:mm:ss"
			} else {
				query.setParameter("value", newValue);
			}

			int updated = query.executeUpdate();
			tx.commit();
			System.out.println(updated);
			return updated > 0;
		} catch (Exception e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
			return false;
		}
	}
}
