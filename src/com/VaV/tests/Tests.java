package com.VaV.tests;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import com.VaV.persistence.*;
import com.VaV.execute.Fill;
import com.VaV.model.*;

public class Tests {

	@Before
	public void setUp() {
		try {
			Fill f = new Fill();
			f.fill_basics();
		}
		catch (Exception e) {}
		try {
			Fill f = new Fill();
			f.fill_database();
		}
		catch (Exception e) {}
	}
	
	@Test
	public void checkFill_basics() {
		AirportDAO aDAO = new AirportDAO();
		ArrayList<Airport> result = new ArrayList<Airport>(aDAO.retrieveAll());
		
		// We should have two airports in the DB
		assertTrue(result.size() == 2);
		
		UserDAO uDAO = new UserDAO();
		User user = new User();
		User admin = new User();
		user.set("Nom d'user", "Prénom d'user", "user", "user", User.USER);
		admin.set("Nom d'admin", "Prénom d'admin", "admin", "admin", User.ADMIN);
		user = uDAO.find(user);
		admin = uDAO.find(admin);

		// We should have these two user in the DB
		assertTrue(user != null);
		assertTrue(admin != null);
	}

	@Test
	public void checkFill_database() {
		ReservationDAO rDAO = new ReservationDAO();
		FlightDAO fDAO = new FlightDAO();
		ArrayList<Reservation> rL = new ArrayList<Reservation>(rDAO.retrieveAll());
		ArrayList<Flight> fL = new ArrayList<Flight>(fDAO.retrieveAll());
		
		/* We check that the reservation is between two different flights */
		for(Reservation current : rL) {
			assertTrue(current.getFlight_outbound() != current.getFlight_return());
		}
		
		/* We check that the flight is between two different airports */
		for(Flight current : fL) {
			assertTrue(current.getAirport_depart() != current.getAirport_arrival());
		}
		
	}
}
