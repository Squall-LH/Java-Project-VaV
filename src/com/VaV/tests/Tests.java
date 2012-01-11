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
		
		// On devrait avoir deux aéroports dans la BDD
		assertTrue(result.size() == 2);
		
		UserDAO uDAO = new UserDAO();
		User user = new User();
		User admin = new User();
		user.set("Nom d'user", "Prénom d'user", "user", "user", 0, User.USER);
		admin.set("Nom d'admin", "Prénom d'admin", "admin", "admin", 0, User.ADMIN);
		user = uDAO.find(user);
		admin = uDAO.find(admin);

		// On devrait avoir ces deux Users de base
		assertTrue(user != null);
		assertTrue(admin != null);
	}

	@Test
	public void checkFill_database() {
		ReservationDAO rDAO = new ReservationDAO();
		FlightDAO fDAO = new FlightDAO();
		ArrayList<Reservation> rL = new ArrayList<Reservation>(rDAO.retrieveAll());
		ArrayList<Flight> fL = new ArrayList<Flight>(fDAO.retrieveAll());
		
		for(Reservation current : rL) {
			assertTrue(current.getFlight_outbound() != current.getFlight_return());
		}
		
		for(Flight current : fL) {
			assertTrue(current.getAirport_depart() != current.getAirport_arrival());
		}
		
	}
}
