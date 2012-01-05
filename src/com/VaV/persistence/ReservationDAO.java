package com.VaV.persistence;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Query;

import com.VaV.model.Reservation;
import com.VaV.model.User;

public class ReservationDAO extends DAO<Reservation> {

	public ReservationDAO() {
		
	}
	
	public ArrayList<Reservation> retriveInsider(Date d1, Date d2, User u) {
		ArrayList<Reservation> results = new ArrayList<Reservation>();
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd H:m:s");
		
		em = factory.createEntityManager();
		em.getTransaction().begin();
		
		Query query = em.createQuery("SELECT r FROM Reservation r join r.flight_outbound f1 join r.flight_return f2 WHERE (?1 between f1.date and f2.date) and (?2 between f1.date and f2.date) and r.user = ?3");
		query.setParameter(1, format.format(d1));
		query.setParameter(2, format.format(d2));
		query.setParameter(3, u);
		
		results = new ArrayList<Reservation>(query.getResultList());
		
		System.out.println("****************************"+ results);
		
		
		return results;
	}
}
