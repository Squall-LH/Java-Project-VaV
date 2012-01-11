package com.VaV.tests;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import org.junit.Test;
import com.VaV.persistence.*;
import com.VaV.model.*;

public class Tests {

	@Test
	public void checkAvailableAirport() {

		AirportDAO aDAO = new AirportDAO();
		ArrayList<Airport> result = new ArrayList<Airport>(aDAO.retrieveAll());
		
		// We should have 2 Airports in the database
		assertTrue(result.size() == 2);
	}

	@Test
	public void checkBasicUser() {
		UserDAO uDAO = new UserDAO();
		User user = new User();
		User admin = new User();
		user.set("Nom d'user", "Prénom d'user", "user", "user", 0, User.USER);
		admin.set("Nom d'admin", "Prénom d'admin", "admin", "admin", 0, User.ADMIN);
		user = uDAO.find(user);
		admin = uDAO.find(admin);

		// We should have these two basic users
		assertTrue(user != null);
		assertTrue(admin != null);
	}
}
