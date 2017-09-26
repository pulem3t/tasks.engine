/**
 * @author pulem3t
 */
package org.pulem3t.tasks.dao.hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.pulem3t.tasks.dao.UserDAO;
import org.pulem3t.tasks.entry.User;
import org.springframework.beans.factory.annotation.Autowired;

public class UserDAOHibernate implements UserDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private Session session;
	private Transaction tx;
	private CriteriaBuilder builder;
	private CriteriaQuery<User> query;
	private Logger logger = Logger.getLogger(UserDAOHibernate.class);

	@Override
	public List<User> getUsers() {
		List<User> userList;
		try {
			session = sessionFactory.openSession();
			builder = session.getCriteriaBuilder();
			tx = session.getTransaction();
			session.beginTransaction();
			query = builder.createQuery(User.class);
			userList = session.createQuery(query).getResultList();
			tx.commit();
		} catch (Exception e) {
			logger.error(null, e);
			if (tx != null) {
				tx.rollback();
			}
			return new ArrayList<>();
		}finally{
			if (session != null) {
				session.close();
			}
		}
		return userList;
	}

	@Override
	public User getUser(String id) {
		User user;
		try {
			session = sessionFactory.openSession();
			builder = session.getCriteriaBuilder();
			tx = session.getTransaction();
			session.beginTransaction();
			query = builder.createQuery(User.class);
			Root<User> userRoot = query.from(User.class);
			query.where(builder.equal(userRoot.get("id"), id));
			user = session.createQuery(query).getSingleResult();
			tx.commit();
			session.close();
		} catch (Exception e) {
			logger.error(null, e);
			if (tx != null) {
				tx.rollback();
			}
			return null;
		}finally{
			if (session != null) {
				session.close();
			}
		}
		return user;
	}

	@Override
	public String addUser(User user) {
		String guid;
		try {
			guid = UUID.randomUUID().toString();
			user.setId(guid);
			session = sessionFactory.openSession();
			tx = session.getTransaction();
			session.beginTransaction();
			session.save(user);
			tx.commit();
			session.close();
		} catch (Exception e) {
			logger.error(null, e);
			if (tx != null) {
				tx.rollback();
			}
			return null;
		}finally{
			if (session != null) {
				session.close();
			}
		}
		return guid;
	}

	@Override
	public void deleteUser(String id) {
		try {
			User user = new User();
			user.setId(id);
			session = sessionFactory.openSession();
			tx = session.getTransaction();
			session.beginTransaction();
			session.remove(user);
			tx.commit();
			session.close();
		} catch (Exception e) {
			logger.error(null, e);
			if (tx != null) {
				tx.rollback();
			}
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public void updateUser(User user) {
		try {
			session = sessionFactory.openSession();
			tx = session.getTransaction();
			session.beginTransaction();
			session.saveOrUpdate(user);
			tx.commit();
			session.close();
		} catch (Exception e) {
			logger.error(null, e);
			if (tx != null) {
				tx.rollback();
			}
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
}
