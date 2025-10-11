package com.sportyfi.dao;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sportyfi.entity.Profiles;
import com.sportyfi.entity.Users;

@Repository
public class SportyfiUserDaoImpl implements SportyfiUserDao {

	@Autowired
    private SessionFactory sessionFactory;
	
	private static final Logger log = LoggerFactory.getLogger(SportyfiUserDao.class);


	@Override
//	@Transactional
    public void saveUser(Users user) {
		Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
	        tx = session.beginTransaction();

	        session.persist(user);  // Hibernate handles the SQL generation
	        tx.commit();
//	        createProfileForUser(user.getId(), user.getCreatedAt(), user.getUserType().toString());
	    } catch (Exception e) {
	        if (tx != null) tx.rollback();
	        log.error("---ExceptionsaveUser1---", e);
	    }
        createProfileForUser(user.getId(), user.getCreatedAt(), user.getUserType().toString(), null);
    }
	
	public void saveGoogleUser(Users user, String name) {
		Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
	        tx = session.beginTransaction();

	        session.save(user);  // Hibernate handles the SQL generation
	        tx.commit();
//	        createProfileForUser(user.getId(), user.getCreatedAt(), user.getUserType().toString());
	    } catch (Exception e) {
	        if (tx != null) tx.rollback();
	        log.error("---ExceptionsaveUser1---", e);
	    }
        createProfileForUser(user.getId(), user.getCreatedAt(), user.getUserType().toString(), name);
    }

	@Override
	@Transactional
    public void updateUser(Users user) {
//        sessionFactory.getCurrentSession().update(user);
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		session.update(user);

		tx.commit();
		session.close();
    }

	@Override
	@Transactional
	public Users findByEmail(String email) {
	    Session session = null;
	    Users user = null;
	    try {
	        session = sessionFactory.openSession();
	        session.beginTransaction();

	        String hql = "FROM Users u WHERE u.email = :email";
	        user = session.createQuery(hql, Users.class)
	                      .setParameter("email", email)
	                      .uniqueResult();

	        session.getTransaction().commit();
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
	    return user;
	}

	@Override
	@Transactional
    public boolean existsByEmail(String email) {
        String hql = "SELECT COUNT(u.id) FROM User u WHERE u.email = :email";
        Long count = sessionFactory.getCurrentSession()
            .createQuery(hql, Long.class)
            .setParameter("email", email)
            .uniqueResult();
        return count > 0;
    }
	
//	public void createProfileForUser(UUID userId, LocalDateTime createdAt, String userType) {
//	    Transaction tx = null;
//	    try (Session session = sessionFactory.openSession()) {
//	        tx = session.beginTransaction();
//
//	        String sql = """
//	            INSERT INTO public.profiles (id, created_at, role)
//	            SELECT :id, :createdAt, :role
//	            WHERE NOT EXISTS (
//	                SELECT 1 FROM public.profiles WHERE id = :id
//	            )
//	        """;
//
//	        session.createNativeQuery(sql)
//	            .setParameter("id", userId)
//	            .setParameter("createdAt", createdAt)
//	            .setParameter("role", userType)
//	            .executeUpdate();
//
//	        tx.commit();
//	    } catch (Exception e) {
//	        if (tx != null) tx.rollback();
//	        e.printStackTrace();
//	    }
//	}
	
	public void createProfileForUser(UUID userId, LocalDateTime createdAt, String userType, String name) {
	    Transaction tx = null;
	    try (Session session = sessionFactory.openSession()) {
	        tx = session.beginTransaction();

	        // First check if profile already exists for this user
	        Profiles existingProfile = session.get(Profiles.class, userId);
	        if (existingProfile == null) {
	            Profiles profile = new Profiles();
	            profile.setId(userId);
	            profile.setUsername(name);
	            profile.setCreated_at(createdAt);
	            profile.setUpdated_at(createdAt);
	            profile.setRole(userType);

	            session.persist(profile); // Hibernate handles insert
	        }
	        tx.commit();
	    } catch (Exception e) {
	        if (tx != null) tx.rollback();
	        log.error("---ExceptioncreateProfileForUser---", e);
	    }
	}
	
	@Override
    public Users getUserById(UUID userId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Users.class, userId);
        }
    }
}

