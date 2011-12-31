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

import com.VaV.execute.Fill;
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
		else if(action.equals("fill_database")) {
			Fill f = new Fill();
			f.fill_database();
			
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
				String notice = new String("Réservation impossible du fait de chevauchement avec des réservations précédentes");
				session.setAttribute("notice", notice);
			}
			else {
				session.setAttribute("user", user2);
			}
		}
		else if(action.equals("logout")) {
			session.invalidate();
		}
		else if(action.equals("seek")) {
			Airport airport_depart = new Airport(request.getParameter("depart"));
			Airport airport_arrival = new Airport(request.getParameter("arrival"));
			FlightDAO fDAO = new FlightDAO();
			Flight f_depart = new Flight();
			Flight f_arrival = new Flight();
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
			SimpleDateFormat format_heure = new SimpleDateFormat("dd/MM/yy H:m");
		    Date date_depart;
		    Date date_arrival;

			try {
				date_depart = format.parse(request.getParameter("date_depart"));
				date_arrival = format.parse(request.getParameter("date_arrival"));
				
				f_depart.set(airport_depart, airport_arrival, null, date_depart);
				f_arrival.set(airport_arrival, airport_depart, null, date_arrival);
				
				ArrayList<Flight> lf_depart;
				ArrayList<Flight> lf_arrival;
				lf_depart = fDAO.retrieveFlight(f_depart);
				lf_arrival = fDAO.retrieveFlight(f_arrival);
				
				if(lf_depart.size() == 0 || lf_arrival.size() == 0) {
					System.out.println("********************* No result");
					String notice = new String("Aucun vol correspondant à ces critères");
					session.setAttribute("notice", notice);
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
			
			/* On récupère la date d'aujourd'hui */
			Date date = Calendar.getInstance().getTime();
			
			depart = fDAO.find(depart);
			arrival = fDAO.find(arrival);
			
			/* On vérifie qu'il n'y a pas de chevauchement de date de vol -Fonctionne à priori, mais à tester d'avantage- */
			ArrayList<Reservation> results = new ArrayList<Reservation>();
			results = rDAO.retriveInsider(depart.getDate(), arrival.getDate(), u);
			if(results == null || results.size() > 0) {
				String notice = new String("Réservation impossible du fait de chevauchement avec des réservations précédentes");
				session.setAttribute("notice", notice);
			}
			else {
				r.set(depart, arrival, u, date);
				rDAO.create(r);
				
				String notice = new String("Réservation effectiée");
				session.setAttribute("notice", notice);
			}
		} else if(action.equals("list_reservation")) {
			UserDAO uDAO = new UserDAO();
			User u = new User((User)session.getAttribute("user"));
			u = uDAO.find(u);
			ReservationDAO rDAO = new ReservationDAO();
			Reservation r = new Reservation();
			
			r.setUser(u);
			ArrayList<Reservation> lR = new ArrayList<Reservation>(rDAO.retrieve(r));
			ArrayList<String> lRS_before = new ArrayList<String>();
			ArrayList<String> lRS_after = new ArrayList<String>();
			ArrayList<String> lRS_after_id = new ArrayList<String>();
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd H:m:s");
			Date now = Calendar.getInstance().getTime();
			for(Reservation current : lR) {
				String tmp = new String("Vol aller-retour de " + current.getFlight_outbound().getAirport_depart().getName() + 
						" à " + current.getFlight_outbound().getAirport_arrival().getName() +
						".\n Date Aller : " + format.format(current.getFlight_outbound().getDate()) + "\n Date Retour : " 
						+ format.format(current.getFlight_return().getDate())
						);
				/* On compare la date du vol de retour à la date d'aujourd'hui pour savoir s'il est encore possible d'annuler au moins le vol de retour ou pas */
				if(now.after(current.getFlight_return().getDate()) ) {
					lRS_before.add(tmp);
				}
				else {
					lRS_after_id.add(String.valueOf(current.getId()));
					lRS_after.add(tmp);
				}
				
				
				System.out.println(current.getId());
			}
			
			session.setAttribute("lRS_before", lRS_before);
			session.setAttribute("lRS_after", lRS_after);
			session.setAttribute("lRS_after_id", lRS_after_id);
			disp = request.getRequestDispatcher("list_reservation.jsp");
		}
		
		if(disp == null )
			disp = request.getRequestDispatcher("index.jsp");
		disp.forward(request, response);
	}

}
