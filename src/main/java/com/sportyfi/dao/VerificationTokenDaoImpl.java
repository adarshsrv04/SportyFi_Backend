package com.sportyfi.dao;

import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sportyfi.entity.EmailVerificationToken;

@Repository
public class VerificationTokenDaoImpl implements VerificationTokenDao {

	@Autowired
    private SessionFactory sessionFactory;

	public void saveToken(EmailVerificationToken token) {
	    Session session = sessionFactory.openSession();
	    Transaction tx = null;
	    try {
	        tx = session.beginTransaction();
	        session.save(token);
	        tx.commit();
	    } catch (Exception e) {
	        if (tx != null) tx.rollback();
	        throw e;
	    } finally {
	        session.close();
	    }
	}
    
    public EmailVerificationToken findByToken(String token) {
        Session session = sessionFactory.openSession();
        try {
            String hql = "FROM EmailVerificationToken v WHERE v.token = :token";
            return session.createQuery(hql, EmailVerificationToken.class)
                          .setParameter("token", UUID.fromString(token)) // assuming token is UUID
                          .uniqueResult();
        } finally {
            session.close();
        }
    }


    public void deleteToken(String token) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            EmailVerificationToken tokenEntity = session.get(EmailVerificationToken.class, UUID.fromString(token));
            if (tokenEntity != null) {
                session.delete(tokenEntity);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
