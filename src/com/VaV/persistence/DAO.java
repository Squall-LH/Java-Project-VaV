package com.VaV.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public abstract class DAO<T> {

	EntityManagerFactory factory;

	public DAO(T obj) {
		factory = Persistence.createEntityManagerFactory("vav");
	}

	public void create(T obj) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		em.persist(obj);
		em.getTransaction().commit();
		em.close();
	}

	public void delete(T obj) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		em.remove(obj);
		em.getTransaction().commit();
		em.close();
	}

	public void update(T obj) {

	}

	public T findById(T id) {

		return null;
	}
}
