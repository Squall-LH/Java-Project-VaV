package com.VaV.persistence;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.queries.ReadAllQuery;
import org.eclipse.persistence.queries.ReadObjectQuery;
import org.eclipse.persistence.queries.SQLCall;

public abstract class DAO<T> {

	protected EntityManagerFactory factory;
	protected EntityManager em;
	
	public DAO() {
		factory = Persistence.createEntityManagerFactory("vav");
	}

	public void create(T obj) {
		em = factory.createEntityManager();
		em.getTransaction().begin();
		em.persist(obj);
		em.getTransaction().commit();
		em.close();
	}

	public void delete(T obj) {
		em = factory.createEntityManager();
		em.getTransaction().begin();
		obj = find(obj);
		obj = em.merge(obj);
		em.remove(obj);
		em.getTransaction().commit();
		em.close();
	}

	public void update() {	
		em = factory.createEntityManager();
		em.getTransaction().begin();
		em.getTransaction().commit();
		em.close();
	}

	public T find(T obj) {
		em = factory.createEntityManager();
		em.getTransaction().begin();
		ReadObjectQuery query = new ReadObjectQuery();
		query.setExampleObject(obj);
		JpaEntityManager jpa = (JpaEntityManager) em.getDelegate();
		T result = (T) jpa.getServerSession().acquireClientSession().executeQuery(query);
		
		return result;
	}
	
	public List<T> retrieve(T obj) {
		em = factory.createEntityManager();
		em.getTransaction().begin();
		ReadAllQuery query = new ReadAllQuery();
		query.setExampleObject(obj);
		JpaEntityManager jpa = (JpaEntityManager) em.getDelegate();
		List<T> result = (List<T>) jpa.getServerSession().acquireClientSession().executeQuery(query);
		
		return result;
	}
	
	public void erase_database() {
		JpaEntityManager jpa = (JpaEntityManager) em.getDelegate();
		jpa.getServerSession().acquireClientSession().executeNonSelectingCall(new SQLCall("SELECT * FROM EMPLOYEE WHERE DEPT_ID =44"));
	}
	
	
}
