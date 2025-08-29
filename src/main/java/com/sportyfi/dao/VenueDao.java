package com.sportyfi.dao;

import com.sportyfi.entity.VenueRequest;
import com.sportyfi.entity.Venues;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
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
		List<Venues> venues = session.createQuery("FROM Venues", Venues.class).list();
		session.close();
		return venues;
	}

	public List<Venues> searchVenues(String sport, String location, int minPrice, int maxPrice, String searchQuery) {

		try (Session session = sessionFactory.openSession()) {
			StringBuilder hql = new StringBuilder("FROM Venues v WHERE price_per_hour <= :maxPrice");

			if (sport != null && !sport.isBlank() && !"All Sports".equalsIgnoreCase(sport)) {
				hql.append(" AND v.sport = :sport");
			}

			if (location != null && !location.isBlank() && !"All Locations".equalsIgnoreCase(location)) {
				hql.append(" AND v.location = :location");
			}

			if (searchQuery != null && !searchQuery.isBlank()) {
				hql.append(" AND LOWER(v.name) LIKE :searchQuery");
			}

			Query<Venues> query = session.createQuery(hql.toString(), Venues.class);

// mandatory params
			query.setParameter("maxPrice", maxPrice);

// optional params
			if (sport != null && !sport.isBlank() && !"All Sports".equalsIgnoreCase(sport)) {
				query.setParameter("sport", sport);
			}
			if (location != null && !location.isBlank() && !"All Locations".equalsIgnoreCase(location)) {
				query.setParameter("location", location);
			}
			if (searchQuery != null && !searchQuery.isBlank()) {
				query.setParameter("searchQuery", "%" + searchQuery.toLowerCase() + "%");
			}

			return query.list();
		}
	}
}
