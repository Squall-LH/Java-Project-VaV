package com.VaV.persistence;

import java.util.List;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.queries.ReadAllQuery;

import com.VaV.model.Airport;

public class AirportDAO extends DAO<Airport> {

	public AirportDAO() {
		
	}
	
	public List<Airport> retrieveAll() {
		em = factory.createEntityManager();
		em.getTransaction().begin();
		
		ReadAllQuery query = new ReadAllQuery();
		Airport obj = new Airport();
		query.setExampleObject(obj);
		 
		JpaEntityManager jpa = (JpaEntityManager) em.getDelegate();
		List<Airport> results = (List<Airport>) jpa.getServerSession().acquireClientSession().executeQuery(query);
		return results;
	}
}
