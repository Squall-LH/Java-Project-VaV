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
import javax.servlet.ServletContext;
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

		/*
		Airport airport = new Airport();
		AirportDAO aDAO = new AirportDAO();
		airport.setName("Paris");
		airport = aDAO.find(airport);
		airport.setName("Parisss");
		aDAO.update();
		*/ 
		
		//ServletContext sc = request.getServletContext();
		//Integer user_level = (Integer) sc.getAttribute("user_level");

		
		//session.setAttribute("airport", airport);
		disp = request.getRequestDispatcher("index.jsp");
		disp.forward(request, response);
	}

}
