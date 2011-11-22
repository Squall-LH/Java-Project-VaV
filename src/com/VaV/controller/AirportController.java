package com.VaV.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.io.IOException;
import com.VaV.model.*;
import com.VaV.persistence.AirportDAO;

import javax.persistence.*;
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
@WebServlet("/AirportController")
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
	protected void doPost(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher disp;
		HttpSession session = request.getSession();

		Airport airport = new Airport();
		AirportDAO aDAO = new AirportDAO();
		airport.setName("Paris");
		airport = aDAO.find(airport);
		airport.setName("Parisss");
		aDAO.update();
		
		/*
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("vav");
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		airport = em.find(Airport.class, (long)15);
		airport.setName("Parisss");
		em.getTransaction().commit();
		em.close();
		*/
		
		//airport = aDAO.findById((long)1);
		/*airport.setName("Paris");
		airportBefore.setName("Paris");
		airportAfter.setName("Parisss");
		//aDAO.create(airport);
		aDAO.update(airport);
		//airport = aDAO.find(airport);
		//aDAO.delete(airport);
			*/	
		// Read the existing entries and write to console
		/*
		Query q = em.createQuery("select a from Airport a");
		List<Airport> arList = q.getResultList();
		for (Airport a : arList) {
			System.out.println(a);
		}
		System.out.println("Size: " + arList.size());

		em.close();
		*/
		
		session.setAttribute("airport", airport);
		disp = request.getRequestDispatcher("index.jsp");
		disp.forward(request, response);
	}

}
