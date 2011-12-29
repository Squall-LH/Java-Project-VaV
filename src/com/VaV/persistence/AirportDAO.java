package com.VaV.persistence;

import javax.persistence.EntityManager;

import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.queries.ReadObjectQuery;

import com.VaV.model.Airport;

public class AirportDAO extends DAO<Airport> {

	public AirportDAO() {
		
	}
}
