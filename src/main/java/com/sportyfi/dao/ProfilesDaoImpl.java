package com.sportyfi.dao;

import com.sportyfi.dao.ProfilesDao;
import com.sportyfi.entity.Profiles;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class ProfilesDaoImpl implements ProfilesDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Profiles findByUserId(UUID userId) {
		//    	Transaction tx = null;
		Session session = null;
		Profiles userProfile = null;
		try {
			session = sessionFactory.openSession();
			//	        tx = session.beginTransaction();
			userProfile = session.get(Profiles.class, userId);
		} catch (Exception e) {
			if (session != null && session.getTransaction().isActive()) {
				session.getTransaction().rollback();
			}
			throw e;
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return userProfile;
	}

//	@Override
//	public void updateProfile(Profiles profile) {
//		Session session = sessionFactory.getCurrentSession();
//		session.merge(profile);
//	}

	public boolean updateProfile(UUID userId, Profiles profile) {
		Transaction tx = null;
		boolean isUpdated = false;

		try (Session session = sessionFactory.openSession()) {
			tx = session.beginTransaction();

			String sql = "UPDATE profiles SET username = :username, bio = :bio, location = :location, " +
					"primary_sport = :primarySport, avatar_url = :avatarUrl, " +
					"preferred_sports = :preferredSports, updated_at = :updatedAt " +
					"WHERE id = :id";

			NativeQuery<?> query = session.createNativeQuery(sql);
			query.setParameter("username", profile.getUsername());
			query.setParameter("bio", profile.getBio());
			query.setParameter("location", profile.getLocation());
			query.setParameter("primarySport", profile.getPrimary_sport());
			query.setParameter("avatarUrl", profile.getAvatar_url());
			query.setParameter("preferredSports", profile.getPreferred_sports()); // PostgreSQL handles arrays
			query.setParameter("updatedAt", profile.getUpdated_at());
			query.setParameter("id", userId);

			int result = query.executeUpdate();
			tx.commit();

			isUpdated = result > 0;
		} catch (Exception e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		}

		return isUpdated;
	}

}
