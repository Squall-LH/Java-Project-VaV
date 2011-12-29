package com.VaV.persistence;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.queries.ReadAllQuery;
import org.eclipse.persistence.queries.ReadObjectQuery;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.expressions.ExpressionBuilder;
import org.eclipse.persistence.queries.*;

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
		 
		ExpressionBuilder builder = new ExpressionBuilder();
		JpaEntityManager jpa = (JpaEntityManager) em.getDelegate();
		List<Airport> results = (List<Airport>) jpa.getServerSession().acquireClientSession().executeQuery(query);
		return results;
	}
}
