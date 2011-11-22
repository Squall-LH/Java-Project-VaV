package com.VaV.persistence;

import javax.persistence.EntityManager;

import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.queries.ReadObjectQuery;

import com.VaV.model.Airport;

public class AirportDAO extends DAO<Airport> {

	public AirportDAO() {
		
	}
	
	public void update(Airport obj, Airport obj2) {
		/*
		ReadObjectQuery query = new ReadObjectQuery();
		EntityManager em = factory.createEntityManager();
		query.setExampleObject(obj);
		JpaEntityManager jpa = (JpaEntityManager) em.getDelegate();
		obj = (Airport) jpa.getServerSession().acquireClientSession().executeQuery(query);
		em.getTransaction().begin();
		obj = em.merge(obj);
		obj.clone(obj2);
		em.getTransaction().commit();
		em.close();*/
	}
}
