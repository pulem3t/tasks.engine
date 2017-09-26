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
import org.pulem3t.tasks.dao.CommentDAO;
import org.pulem3t.tasks.entry.Comment;
import org.springframework.beans.factory.annotation.Autowired;

public class CommentDAOHibernate implements CommentDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	private Session session;
	private Transaction tx;
	private CriteriaBuilder builder;
	private CriteriaQuery<Comment> query;
	private Logger logger = Logger.getLogger(CommentDAOHibernate.class);
	
	@Override
	public List<Comment> getComments(String taskId) {
		List<Comment> commentList;
		try {
			session = sessionFactory.openSession();
			builder = session.getCriteriaBuilder();
			tx = session.getTransaction();
			session.beginTransaction();
			query = builder.createQuery(Comment.class);
			Root<Comment> commentRoot = query.from(Comment.class);
			query.where(builder.equal(commentRoot.get("taskid"), taskId));
			commentList = session.createQuery(query).getResultList();
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
		return commentList;
	}

	@Override
	public Comment getComment(String id) {
		Comment comment;
		try {
			session = sessionFactory.openSession();
			builder = session.getCriteriaBuilder();
			tx = session.getTransaction();
			session.beginTransaction();
			query = builder.createQuery(Comment.class);
			Root<Comment> commentRoot = query.from(Comment.class);
			query.where(builder.equal(commentRoot.get("id"), id));
			comment = session.createQuery(query).getSingleResult();
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
		return comment;
	}

	@Override
	public String addComment(Comment comment) {
		String guid;
		try {
			guid = UUID.randomUUID().toString();
			comment.setId(guid);
			session = sessionFactory.openSession();
			tx = session.getTransaction();
			session.beginTransaction();
			session.save(comment);
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
	public void deleteComment(String id) {
		try {
			Comment comment = new Comment();
			comment.setId(id);
			session = sessionFactory.openSession();
			tx = session.getTransaction();
			session.beginTransaction();
			session.remove(comment);
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
	public void updateComment(Comment comment) {
		try {
			session = sessionFactory.openSession();
			tx = session.getTransaction();
			session.beginTransaction();
			session.saveOrUpdate(comment);
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
