package com.VaV.persistence;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;
import org.eclipse.persistence.expressions.ExpressionBuilder;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.queries.ReadAllQuery;
import com.VaV.model.Flight;

public class FlightDAO extends DAO<Flight> {

	public FlightDAO() {
		
	}
	
	public ArrayList<Flight> retrieveFlight(Flight f) {
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(f.getDate());
		
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(f.getDate());
		
		calendar1.set(Calendar.HOUR, 0);
		calendar1.set(Calendar.MINUTE, 0);
		
		calendar2.set(Calendar.HOUR, 23);
		calendar2.set(Calendar.MINUTE, 59);
		
		f.setDate(null);
		
		em = factory.createEntityManager();
		em.getTransaction().begin();
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd H:m:s");
		
		/* JPQL */
		Query query = em.createQuery("select f FROM Flight f join f.airport_depart a1 join f.airport_arrival a2 where a1.name = ?1 and a2.name = ?2 and (f.date between ?3 and ?4)" +
		"and f.plane.id IN (select p.id FROM Plane p where (SELECT COUNT(r.id) From Reservation r where r.flight_outbound = f or r.flight_return = f) < p.seats)");
		query.setParameter(1, f.getAirport_depart().getName());
		query.setParameter(2, f.getAirport_arrival().getName());
		query.setParameter(3, calendar1.getTime());
		query.setParameter(4, calendar2.getTime());
		
		ArrayList<Flight> results = new ArrayList<Flight>(query.getResultList());
		
		System.out.println("****************************"+ results);
		
		
		return results;
	}
	
	public List<Flight> retrieveFlight(Date d1, Date d2) {
		em = factory.createEntityManager();
		em.getTransaction().begin();
		
		ReadAllQuery query = new ReadAllQuery();
		query.setExampleObject(new Flight());
		ExpressionBuilder builder = new ExpressionBuilder();
		
		query.setSelectionCriteria(builder.get("date").between(d1,d2));
	
		JpaEntityManager jpa = (JpaEntityManager) em.getDelegate();
		List<Flight> results = (List<Flight>) jpa.getServerSession().acquireClientSession().executeQuery(query);

		return results;
	}
	
	public ArrayList<Long> freeSeats(List<Flight> lF) {
		em = factory.createEntityManager();
		em.getTransaction().begin();
		
		ArrayList<Long> results = new ArrayList<Long>();
		
		for(Flight current : lF) {
			Query query_nb = em.createQuery("SELECT COUNT(r) FROM Reservation r WHERE r.flight_outbound = ?1 OR r.flight_return = ?1");
			Query query_seat = em.createQuery("select p.seats from Flight f JOIN f.plane p where f.id = ?1;");
			query_nb.setParameter(1, current);
			query_seat.setParameter(1, current.getId());
			Long nb = (Long)query_nb.getSingleResult();
			Integer seat = (Integer)query_seat.getSingleResult();
			results.add(seat - nb);
			System.out.println("ID du Vol : " + current.getId() + " | Nombre de Siège libre : " + (seat-nb));
		}

		return results;
	}
}
