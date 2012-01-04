package com.VaV.persistence;

import java.sql.DriverManager;
import java.sql.ResultSet;
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

import java.sql.*;

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
		/*ReadAllQuery query = new ReadAllQuery();
		query.setExampleObject(f);
		
		ExpressionBuilder builder = new ExpressionBuilder();
		
		query.setSelectionCriteria(builder.get("date").between(calendar1,calendar2));
	
		JpaEntityManager jpa = (JpaEntityManager) em.getDelegate();
		List<Flight> results = (List<Flight>) jpa.getServerSession().acquireClientSession().executeQuery(query);
		
		for(Flight fl : results)
			System.out.println(fl.getId());
		
		*/
		
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd H:m:s");
		
		String query = new String("SELECT distinct f.ID, f.DATE, f.AIRPORT_ARRIVAL_ID, f.AIRPORT_DEPART_ID, f.PLANE_ID FROM `FLIGHT` f JOIN `AIRPORT` a1 ON a1.id=f.AIRPORT_DEPART_ID JOIN `AIRPORT` a2 ON a2.id=f.AIRPORT_ARRIVAL_ID WHERE a1.NAME = '"
				+ f.getAirport_depart().getName() + "' and a2.NAME = '" + f.getAirport_arrival().getName() + "' and (f.DATE BETWEEN '" + date.format(calendar1.getTime()) +"' and '" + date.format(calendar2.getTime()) + "')  and f.PLANE_ID IN (SELECT p2.ID FROM `PLANE` p2 WHERE (SELECT COUNT(r.ID) FROM `RESERVATION` r WHERE r.FLIGHT_OUTBOUND_ID = f.ID OR r.FLIGHT_RETURN_ID = f.ID) < p2.SEATS );");
		
		/*
		JpaEntityManager jpa = (JpaEntityManager) em.getDelegate();
		Vector a = (Vector) jpa.getServerSession().acquireClientSession().executeSelectingCall(new SQLCall(query));
		
		System.out.println(a);
		ArrayList<Flight> results = new ArrayList<Flight>();
		Flight fl = new Flight();
		
		for(int i = 0; i < a.size(); i++) {
			if(i % 5 == 0) {
				fl.setId(Integer.getInteger((String)a.get(i)));
				fl = find(fl);
				results.add(fl);
			}
				
		}
		*/
		
		/* JDBC Fonctionne..... yeah.... */
		
	    Connection cnx=null;
	    
	    ArrayList<Flight> results = new ArrayList<Flight>();
		Flight fl = new Flight();
		
		try {
			String url="jdbc:mysql://localhost:3306/vav";
			//Class.forName("com.mysql.jdbc.Driver");
			cnx = (Connection) DriverManager.getConnection(url, "vol", "vent");
			
			System.out.println(query);
			Statement stmt = cnx.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			//rs.next();
			while(rs.next()) {
				String id = new String(rs.getString("id"));
				System.out.println("************************************************************************   " + id);
			
				int id_int = Integer.parseInt(id);
				fl.setId(id_int);
				fl = find(fl);
				results.add(fl);
			}
			
			rs.close();
			cnx.close();
				
		} catch(Exception  e) {
			e.printStackTrace(System.out);
		}
		
		
		/* bug pas... mais ne fonctionne pas...
		 * Query query = em.createQuery("SELECT f.id, f.date, f.airport_arrival, f.airport_depart, f.plane FROM Flight f, Airport a1, Airport a2, Plane p WHERE a1.name = ?1"
				+ " and a2.name = ?2 " + 
				"and ((f.date BETWEEN ?3" 
				+" and ?4)" 
				+ ")  and f.plane.id IN (SELECT p2.id FROM Plane p2 WHERE (SELECT COUNT(r.id) FROM Reservation r WHERE r.flight_outbound.id = f.id OR r.flight_return.id = f.id) < p2.seats )").setParameter(1, f.getAirport_depart().getName());
		 */
		/*
		Query query = em.createQuery("SELECT distinct f.id FROM Flight f JOIN Airport a1 ON a1.id=f.airport_depart.id JOIN Airport a2 ON a2.id=f.airport_arrival.id WHERE a1.name = ?1"
				+ " and a2.name = ?2 " + 
				"and (f.date BETWEEN ?3" 
				+" and ?4)"
				+ "  and f.plane.id IN (SELECT p2.id FROM Plane p2 WHERE (SELECT COUNT(r.id) FROM Reservation r WHERE (r.flight_outbound.id = f.id) OR (r.flight_return.id = f.id) ) < p2.seats )");
		query.setParameter(1, f.getAirport_depart().getName());
		query.setParameter(2, f.getAirport_arrival().getName());
		query.setParameter(3, calendar1);
		query.setParameter(4, calendar2);
		
		ArrayList<Flight> results = new ArrayList<Flight>(query.getResultList());
		
		System.out.println("****************************"+ results);
		*/
		
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
