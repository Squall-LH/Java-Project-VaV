package com.VaV.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.eclipse.persistence.expressions.ExpressionBuilder;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.queries.ReadAllQuery;
import org.eclipse.persistence.queries.ReadObjectQuery;

import com.VaV.model.Airport;

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
	
}
