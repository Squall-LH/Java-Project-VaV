package com.VaV.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.io.IOException;
import com.VaV.model.*;
import com.VaV.persistence.*;

import javax.persistence.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.queries.ReadObjectQuery;

/**
 * Servlet implementation class AirportController
 */
@WebServlet("/user")
public class AirportController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AirportController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher disp = null;
		HttpSession session = request.getSession();
		
		String action = request.getParameter("action");
		
		if(action == null) {
			
		}
		else if(action.equals("init")) {
			
			User user = new User("More", "John", "john", "more", 0, User.USER);
			
			UserDAO userDAO = new UserDAO();
			userDAO.create(user);
			user.set("Plus", "Jean", "jean", "plus", 0, User.USER);
			userDAO.create(user);
			user.set("Nom d'user", "Prénom d'user", "user", "user", 0, User.USER);
			userDAO.create(user);
			user.set("Nom d'admin", "Prénom d'admin", "admin", "admin", 0, User.ADMIN);
			userDAO.create(user);
			
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
			
			Flight f = new Flight();
			FlightDAO fDAO = new FlightDAO();
			
			Calendar c = Calendar.getInstance(TimeZone.getTimeZone("CEST"), Locale.FRANCE);
			
			c.set(2005, Calendar.DECEMBER, 25, 10, 5);
			f.set(airport1, airport2, plane1, c.getTime());
			fDAO.create(f);
			c.set(2005, Calendar.DECEMBER, 30, 20, 30);
			f.set(airport1, airport2, plane2, c.getTime());
			fDAO.create(f);
			c.set(2006, Calendar.JANUARY, 5, 9, 30);
			f.set(airport2, airport1, plane3, c.getTime());
			fDAO.create(f);
			
			AirportDAO aDAO = new AirportDAO();
			
			ArrayList<Airport> lA = new ArrayList<Airport>(aDAO.retrieveAll());
			
			ArrayList<String> lAS = new ArrayList<String>();
			System.out.println("******************************" + lA.size());
			for(Airport a : lA) {
				lAS.add(a.getName());
				System.out.println("Airport : " + a.getName() );
			}
			
			request.getServletContext().setAttribute("airport_list", lAS);
		}
		else if(action.equals("login")) {
			String login = request.getParameter("id");
			String pass = request.getParameter("pass");
			
			session.setAttribute("login", login);
			session.setAttribute("pass", pass);
			
			User user = new User();
			User user2 = new User();
			UserDAO userDAO = new UserDAO();
			
			user.setLogin(login);
			user.setPass(pass);
			
			user2 = userDAO.find(user);
			if(user2 == null) {
				System.out.println("log - pass invalide");
			}
			else {
				session.setAttribute("user", user2);
			}
				
			session.setAttribute("user", user2);
		}
		else if(action.equals("logout")) {
			session.invalidate();
		}
		else if(action.equals("seek")) {
			Airport airport_depart = new Airport(request.getParameter("depart"));
			Airport airport_arrival = new Airport(request.getParameter("arrival"));
			AirportDAO aDAO = new AirportDAO();
			FlightDAO fDAO = new FlightDAO();
			Flight f_depart = new Flight();
			Flight f_arrival = new Flight();
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
			SimpleDateFormat format_heure = new SimpleDateFormat("dd/MM/yy H:m");
		    Date date_depart;
		    Date date_arrival;
		    Calendar calendar_depart = Calendar.getInstance();
		    Calendar calendar_arrival = Calendar.getInstance();
			try {
				date_depart = format.parse(request.getParameter("date_depart"));
				date_arrival = format.parse(request.getParameter("date_arrival"));
				calendar_depart.setTime(date_depart);
				calendar_arrival.setTime(date_arrival);
				calendar_depart.set(Calendar.HOUR, 0);
				calendar_depart.set(Calendar.MINUTE, 0);
				System.out.println(format.format(date_depart));
				System.out.println(format.format(date_arrival));
				
				f_depart.set(airport_depart, airport_arrival, null, date_depart);
				f_arrival.set(airport_arrival, airport_depart, null, date_arrival);
				
				ArrayList<Flight> lf_depart;
				ArrayList<Flight> lf_arrival;
				lf_depart = fDAO.retrieveFlight(f_depart);
				lf_arrival = fDAO.retrieveFlight(f_arrival);
				
				if(lf_depart.size() == 0 || lf_arrival.size() == 0) {
					System.out.println("********************* No result");
					String message = new String("Aucun vol correspondant à ces critères");
					session.setAttribute("message", message);
				}
				else {
					ArrayList<String> flight_depart = new ArrayList<String>();
					ArrayList<String> flight_arrival = new ArrayList<String>();
					for(Flight current : lf_depart) {
						String tmp = new String(format_heure.format(current.getDate()));
						flight_depart.add(tmp);
					}
					
					for(Flight current : lf_arrival) {
						String tmp = new String(format_heure.format(current.getDate()));
						flight_arrival.add(tmp);
					}
					
					session.setAttribute("flight_depart", flight_depart);
					session.setAttribute("flight_arrival", flight_arrival);
					session.setAttribute("lf_depart", lf_depart);
					session.setAttribute("lf_arrival", lf_arrival);
					
					disp = request.getRequestDispatcher("list_flight.jsp");
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else if(action.equals("reserve")) {
			UserDAO uDAO = new UserDAO();
			User u = new User((User)session.getAttribute("user"));
			u = uDAO.find(u);
			
			Flight depart = new Flight();
			Flight arrival = new Flight();
			FlightDAO fDAO = new FlightDAO();
			ReservationDAO rDAO = new ReservationDAO();
			Reservation r = new Reservation();
			
			depart.setId(Integer.parseInt(request.getParameter("depart")));
			arrival.setId(Integer.parseInt(request.getParameter("arrival")));

			Date date = Calendar.getInstance().getTime();
			
			depart = fDAO.find(depart);
			arrival = fDAO.find(arrival);
			
			r.set(depart, arrival, u, date);
			rDAO.create(r);
			
			String message = new String("Réservation effectiée");
			session.setAttribute("message", message);
		}
		
		if(disp == null )
			disp = request.getRequestDispatcher("index.jsp");
		disp.forward(request, response);
	}

}
