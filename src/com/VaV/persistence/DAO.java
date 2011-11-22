package com.VaV.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.queries.ReadObjectQuery;

public abstract class DAO<T> {

	private EntityManagerFactory factory;
	private EntityManager em;
	
	public DAO() {
		factory = Persistence.createEntityManagerFactory("vav");
		em = factory.createEntityManager();
		em.getTransaction().begin();
	}

	public void create(T obj) {
		em.persist(obj);
		em.getTransaction().commit();
	}

	public void delete(T obj) {
		obj = find(obj);
		obj = em.merge(obj);
		em.remove(obj);
		em.getTransaction().commit();
	}

	public void update() {	
		em.getTransaction().commit();
	}

	public T find(T obj) {
		ReadObjectQuery query = new ReadObjectQuery();
		query.setExampleObject(obj);
		JpaEntityManager jpa = (JpaEntityManager) em.getDelegate();
		T result = (T) jpa.getServerSession().acquireClientSession().executeQuery(query);
		return em.merge(result);
	}
}
