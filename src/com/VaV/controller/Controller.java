package com.VaV.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.io.IOException;
import com.VaV.execute.Fill;
import com.VaV.model.*;
import com.VaV.persistence.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Servlet implementation class AirportController
 */
@WebServlet("/controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Controller() {
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
		String Redirect_URL = null;
		String notice = null;
		
		if(action == null) {
			
		}
		else if(action.equals("fill_database")) {
			try {
				Fill f = new Fill();
				f.fill_database();
			}
			catch (Exception e) {notice = new String("La BDD est déjà remplie des données fictives."); session.setAttribute("notice", notice);}
			
			/* We update the airports' name list */
			AirportDAO aDAO = new AirportDAO();
			ArrayList<Airport> lA = new ArrayList<Airport>(aDAO.retrieveAll());
			ArrayList<String> lAS = new ArrayList<String>();
			for(Airport a : lA) {
				lAS.add(a.getName());
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
				notice = new String("Identifiant invalide");
				session.setAttribute("notice", notice);
				disp = request.getRequestDispatcher("login.jsp");
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
			ReservationDAO rDAO = new ReservationDAO();
			Reservation r = new Reservation();
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat format_hour = new SimpleDateFormat("dd/MM/yyyy H:m");
			Date date_outbound;
			Date date_return;

			try {
				date_outbound = format.parse(request.getParameter("date_outbound"));
				date_return = format.parse(request.getParameter("date_return"));
					
				f_depart.set(airport_depart, airport_arrival, null, date_outbound);
				f_arrival.set(airport_arrival, airport_depart, null, date_return);
					
				ArrayList<Flight> lf_depart;
				ArrayList<Flight> lf_arrival;
				lf_depart = fDAO.retrieveFlight(f_depart);
				lf_arrival = fDAO.retrieveFlight(f_arrival);
					
				if(lf_depart.size() == 0 || lf_arrival.size() == 0) {
					notice = new String("Aucun vol correspondant à ces critères");
					session.setAttribute("notice", notice);
				}
				else {
					ArrayList<String> flight_depart = new ArrayList<String>();
					ArrayList<String> flight_arrival = new ArrayList<String>();
					for(Flight current : lf_depart) {
						String tmp = new String(format_hour.format(current.getDate()));
						flight_depart.add(tmp);
					}
						
						for(Flight current : lf_arrival) {
							String tmp = new String(format_hour.format(current.getDate()));
							flight_arrival.add(tmp);
						}
						
						session.setAttribute("flight_depart", flight_depart);
						session.setAttribute("flight_arrival", flight_arrival);
						session.setAttribute("lf_depart", lf_depart);
						session.setAttribute("lf_arrival", lf_arrival);
						
						/* If the user is identified we retrieve his number of reservations to known if he will get a free ticket or not */
						UserDAO uDAO = new UserDAO();
						User u = new User((User)session.getAttribute("user"));
						Integer nb_reservation = 0;
						if(u != null || u.getLevel() != User.VISITOR) {
							u = uDAO.find(u);
							r.setUser(u);
							ArrayList<Reservation> lR = new ArrayList<Reservation>(rDAO.retrieve(r));
							nb_reservation = lR.size();
						}

						session.setAttribute("nb_reservation", nb_reservation);
						disp = request.getRequestDispatcher("list_flight.jsp");
				}
				
				session.setAttribute("date_outbound", format.format(date_outbound));
				session.setAttribute("date_return", format.format(date_return));
				
				} catch (ParseException e) {
					e.printStackTrace();
				}
		} else if(action.equals("reserve")) {
			UserDAO uDAO = new UserDAO();
			User u = new User((User)session.getAttribute("user"));
			
			if(u == null|| u.getLevel() == User.VISITOR) {
				notice = new String("Veuillez vous identifier avant de valider une réservation");
				session.setAttribute("notice", notice);
				disp = request.getRequestDispatcher("list_flight.jsp");
			}
			else {
				u = uDAO.find(u);
				Flight depart = new Flight();
				Flight arrival = new Flight();
				FlightDAO fDAO = new FlightDAO();
				ReservationDAO rDAO = new ReservationDAO();
				Reservation r = new Reservation();
				
				depart.setId(Integer.parseInt(request.getParameter("depart")));
				arrival.setId(Integer.parseInt(request.getParameter("arrival")));
				
				/* We get today's time */
				Date date = Calendar.getInstance().getTime();
				
				depart = fDAO.find(depart);
				arrival = fDAO.find(arrival);
				
				/* We verify that there is no overlap of flight date */
				ArrayList<Reservation> results = new ArrayList<Reservation>();
				results = rDAO.retrieveBetween(depart.getDate(), arrival.getDate(), u);
				if(results == null || results.size() > 0) {
					notice = new String("Réservation impossible du fait de chevauchement avec des réservations précédentes");
					session.setAttribute("notice", notice);
				}
				else {
					r.set(depart, arrival, u, date);
					rDAO.create(r);
					notice = new String("Réservation effectiée");
					session.setAttribute("notice", notice);
				}
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
						"<br /> Date Aller : " + format.format(current.getFlight_outbound().getDate()) + "<br /> Date Retour : " 
						+ format.format(current.getFlight_return().getDate())
						);
				/* We separate past and future reservation 
				 * If the return date is later than today, it is possible to cancel the reservation so we get the id too */
				if(now.after(current.getFlight_return().getDate()) ) {
					lRS_before.add(tmp);
				}
				else {
					lRS_after_id.add(String.valueOf(current.getId()));
					lRS_after.add(tmp);
				}
			}
			
			session.setAttribute("lRS_before", lRS_before);
			session.setAttribute("lRS_after", lRS_after);
			session.setAttribute("lRS_after_id", lRS_after_id);
			disp = request.getRequestDispatcher("list_reservation.jsp");
		} else if(action.equals("remove")) {
			ReservationDAO rDAO = new ReservationDAO();
			
			/* We get the parameter names and try to parse them as Integer. If it's working then we consider them as reservation's id
			 * to be removed */
			Enumeration<String> p = request.getParameterNames();
			

			while(p.hasMoreElements()) {
				try {
					Integer id = Integer.valueOf(p.nextElement());
					Reservation r = new Reservation();
					r.setId(id);
					r = rDAO.find(r);
					rDAO.delete(r);
				} catch (Exception e) {}
			}
			
			notice = new String("Réservations annulées");
			session.setAttribute("notice", notice);
			Redirect_URL = response.encodeURL("controller?action=list_reservation");
		} else if(action.equals("view_flight")) {
			/* Allow admin to see all flights between and their free seats two dates */
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat format_2 = new SimpleDateFormat("yyyy-MM-dd H:m:s");
			Date d1 = null;
			Date d2 = null;
			
			try {
				d1 = format.parse(request.getParameter("date1"));
				d2 = format.parse(request.getParameter("date2"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			FlightDAO fDAO = new FlightDAO();
			ArrayList<Flight> lF = new ArrayList<Flight>(fDAO.retrieveBetween(d1, d2));
			ArrayList<Long> freeSeats = new ArrayList<Long>(fDAO.freeSeats(lF));
			
			if(lF.size() > 0) {
				ArrayList<String> lFlightS = new ArrayList<String>();
				
				for(int i = 0; i < lF.size(); i++) {
					String tmp = new String("Vol de " + lF.get(i).getAirport_depart().getName() + 
							" à " + lF.get(i).getAirport_arrival().getName() +
							".<br /> Date du Vol : " + format_2.format(lF.get(i).getDate()) + 
							"<br />Nombre de place libres : " + freeSeats.get(i) 
							);
					lFlightS.add(tmp);
				}

				session.setAttribute("lFlightS", lFlightS);
			} else {
				notice = new String("Il n'existe pas de vol entre ces dates");
				session.setAttribute("notice", notice);
			}
			
			session.setAttribute("date1", format.format(d1));
			session.setAttribute("date2", format.format(d2));
			disp = request.getRequestDispatcher("view_flight.jsp");
		}
		
		if(Redirect_URL != null) {
			response.sendRedirect(Redirect_URL);
		}
		else if(disp == null ) {
			disp = request.getRequestDispatcher("index.jsp");
			disp.forward(request, response);
		}
		else {
			disp.forward(request, response);
		}
	}

}
