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
}
