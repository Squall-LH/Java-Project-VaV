package com.VaV.execute;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import com.VaV.model.Airport;
import com.VaV.model.Flight;
import com.VaV.model.Plane;
import com.VaV.model.Reservation;
import com.VaV.model.User;
import com.VaV.persistence.AirportDAO;
import com.VaV.persistence.FlightDAO;
import com.VaV.persistence.PlaneDAO;
import com.VaV.persistence.ReservationDAO;
import com.VaV.persistence.UserDAO;

public class Fill {
	public void fill_database() {
		User user = new User("More", "John", "john", "more", 0, User.USER);
		
		UserDAO userDAO = new UserDAO();
		userDAO.create(user);
		user.set("Plus", "Jean", "jean", "plus", 0, User.USER);
		userDAO.create(user);
		/*user.set("Nom d'user", "Prénom d'user", "user", "user", 0, User.USER);
		userDAO.create(user);
		user.set("Nom d'admin", "Prénom d'admin", "admin", "admin", 0, User.ADMIN);
		userDAO.create(user);*/
		
		AirportDAO airportDAO = new AirportDAO();
		Airport airport1 = new Airport();
		Airport airport2 = new Airport();
		
		airport1.setName("Paris Charles de Gaulle (CDG)");
		airportDAO.create(airport1);
		airport2.setName("Newark Liberty International (EWR)");
		airportDAO.create(airport2);
		
		PlaneDAO planeDAO = new PlaneDAO();
		Plane plane1 = new Plane();
		Plane plane2 = new Plane();
		Plane plane3 = new Plane();
		
		plane1.setName("Avion 1");
		plane1.setSeats(10);
		planeDAO.create(plane1);
		
		plane2.setName("Avion 2");
		plane2.setSeats(20);
		planeDAO.create(plane2);
		
		plane3.setName("Avion 3");
		plane3.setSeats(30);
		planeDAO.create(plane3);
		
		Flight f1 = new Flight();
		Flight f2 = new Flight();
		Flight f3 = new Flight();
		Flight f4 = new Flight();
		FlightDAO fDAO = new FlightDAO();
		
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("CEST"), Locale.FRANCE);
		
		c.set(2005, Calendar.DECEMBER, 25, 10, 5);
		f1.set(airport1, airport2, plane1, c.getTime());
		fDAO.create(f1);
		c.set(2005, Calendar.DECEMBER, 30, 20, 30);
		f2.set(airport1, airport2, plane2, c.getTime());
		fDAO.create(f2);
		c.set(2006, Calendar.JANUARY, 5, 9, 30);
		f3.set(airport2, airport1, plane3, c.getTime());
		fDAO.create(f3);
		c.set(2012, Calendar.MARCH, 5, 9, 30);
		f4.set(airport2, airport1, plane3, c.getTime());
		fDAO.create(f4);
		
		Reservation r1 = new Reservation();
		Reservation r2 = new Reservation();
		ReservationDAO rDAO = new ReservationDAO();
		
		User user3 = new User("More", "John", "john", "more", 0, User.USER);
		User user4 = new User("Plus", "Jean", "jean", "plus", 0, User.USER);
		UserDAO uDAO = new UserDAO();
		user3 = uDAO.find(user3);
		user4 = uDAO.find(user4);
		
		r1.set(f1, f2, user3, c.getTime()); rDAO.create(r1);
		c.set(2010, Calendar.MARCH, 6, 10, 20);
		r2.set(f2, f1, user4, c.getTime()); rDAO.create(r2);
	}
	
	public void fill_basic_user() {
		User user = new User();
		UserDAO userDAO = new UserDAO();
		
		user.set("Nom d'user", "Prénom d'user", "user", "user", 0, User.USER);
		userDAO.create(user);
		user.set("Nom d'admin", "Prénom d'admin", "admin", "admin", 0, User.ADMIN);
		userDAO.create(user);
	}
}
