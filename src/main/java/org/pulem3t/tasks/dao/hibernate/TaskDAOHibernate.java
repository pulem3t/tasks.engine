/**
 * @author pulem3t
 */
package org.pulem3t.tasks.dao.hibernate;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.pulem3t.tasks.dao.TaskDAO;
import org.pulem3t.tasks.entry.Task;
import org.springframework.beans.factory.annotation.Autowired;

public class TaskDAOHibernate implements TaskDAO {

	@Autowired
	private SessionFactory sessionFactory;

	private Session session;
	private Transaction tx;
	private CriteriaBuilder builder;
	private CriteriaQuery<Task> query;
	private Logger logger = Logger.getLogger(TaskDAOHibernate.class);

	@Override
	public List<Task> getTasks() {
		List<Task> taskList;
		try {
			session = sessionFactory.openSession();
			builder = session.getCriteriaBuilder();
			tx = session.getTransaction();
			session.beginTransaction();
			query = builder.createQuery(Task.class);
			taskList = session.createQuery(query).getResultList();
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
		return taskList;
	}

	@Override
	public Task getTask(String id) {
		Task task;
		try {
			session = sessionFactory.openSession();
			builder = session.getCriteriaBuilder();
			tx = session.getTransaction();
			session.beginTransaction();
			query = builder.createQuery(Task.class);
			Root<Task> taskRoot = query.from(Task.class);
			query.where(builder.equal(taskRoot.get("id"), id));
			task = session.createQuery(query).getSingleResult();
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
		return task;
	}

	@Override
	public List<Task> getLastAddedTasks(){
		List<Task> lastTenList;
		try {
			session = sessionFactory.openSession();
			builder = session.getCriteriaBuilder();
			tx = session.getTransaction();
			session.beginTransaction();
			Comparator<Task> c = (Task o1, Task o2) -> (o1.getCreateDate() <= o2.getCreateDate()) ? 1: -1;
			query = builder.createQuery(Task.class);
			List<Task> tasklist = session.createQuery(query).getResultList();
			tx.commit();
			session.close();
			lastTenList = new ArrayList<Task>();
			tasklist.stream().sorted(c).limit(10).forEach(lastTenList::add);
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
		return lastTenList;
	}

	@Override
	public String addTask(Task task) {
		String guid;
		try {
			guid = UUID.randomUUID().toString();
			task.setId(guid);
			session = sessionFactory.openSession();
			tx = session.getTransaction();
			session.beginTransaction();
			session.save(task);
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
	public void deleteTask(String id) {
		try {
			Task task = new Task();
			task.setId(id);
			session = sessionFactory.openSession();
			tx = session.getTransaction();
			session.beginTransaction();
			session.remove(task);
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
	public void updateTask(Task task) {
		try {
			session = sessionFactory.openSession();
			tx = session.getTransaction();
			session.beginTransaction();
			session.saveOrUpdate(task);
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
